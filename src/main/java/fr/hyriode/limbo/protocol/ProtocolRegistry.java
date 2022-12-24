package fr.hyriode.limbo.protocol;

import fr.hyriode.limbo.protocol.packet.Packet;
import fr.hyriode.limbo.protocol.packet.PacketIn;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.PacketOut;

import java.util.*;

/**
 * Created by AstFaster
 * on 21/12/2022 at 19:03
 */
public class ProtocolRegistry {

    private final Map<ProtocolState, List<RegisteredPacket>> packets = new IdentityHashMap<>();
    private final Map<Class<? extends PacketIn>, PacketInHandler<? extends PacketIn>> inputHandlers = new IdentityHashMap<>();

    public <P extends PacketIn> void registerIn(ProtocolState protocolState, int packetId, Class<P> packetClass) {
        final List<RegisteredPacket> registeredPackets = this.packets.getOrDefault(protocolState, new ArrayList<>());

        registeredPackets.add(new RegisteredPacket(ProtocolBound.SERVER, packetId, packetClass));

        this.packets.put(protocolState, registeredPackets);
    }

    public <P extends PacketOut> void registerOut(ProtocolState protocolState, int packetId, Class<P> packetClass) {
        final List<RegisteredPacket> registeredPackets = this.packets.getOrDefault(protocolState, new ArrayList<>());

        registeredPackets.add(new RegisteredPacket(ProtocolBound.CLIENT, packetId, packetClass));

        this.packets.put(protocolState, registeredPackets);
    }

    public <P extends PacketIn> void registerHandler(Class<P> packetClass, PacketInHandler<P> handler) {
        this.inputHandlers.put(packetClass, handler);
    }

    public Optional<Integer> getPacketId(ProtocolState protocolState, Class<? extends Packet> packetClass) {
        final List<RegisteredPacket> registeredPackets = this.packets.get(protocolState);

        if (registeredPackets == null) {
            return Optional.empty();
        }

        for (RegisteredPacket registeredPacket : registeredPackets) {
            if (registeredPacket.packetClass() == packetClass) {
                return Optional.of(registeredPacket.packetId());
            }
        }
        return Optional.empty();
    }

    public Optional<Integer> getPacketId(ProtocolState protocolState, Packet packet) {
        return this.getPacketId(protocolState, packet.getClass());
    }

    public Optional<PacketIn> getPacketById(ProtocolState protocolState, int packetId) throws ReflectiveOperationException {
        final List<RegisteredPacket> registeredPackets = this.packets.get(protocolState);

        if (registeredPackets == null) {
            return Optional.empty();
        }

        for (RegisteredPacket packet : registeredPackets) {
            if (packet.bound() == ProtocolBound.CLIENT) {
                continue;
            }

            if (packet.packetId() == packetId) {
                return Optional.of(packet.constructPacket());
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <P extends PacketIn> Optional<PacketInHandler<P>> getHandler(Class<P> packetClass) {
        return Optional.ofNullable((PacketInHandler<P>) this.inputHandlers.get(packetClass));
    }

    private record RegisteredPacket(ProtocolBound bound, int packetId, Class<? extends Packet> packetClass) {

        public PacketIn constructPacket() throws ReflectiveOperationException {
            return (PacketIn) this.packetClass.getConstructor().newInstance();
        }

    }

}
