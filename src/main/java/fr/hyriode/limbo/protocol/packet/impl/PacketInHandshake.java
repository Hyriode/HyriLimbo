package fr.hyriode.limbo.protocol.packet.impl;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 21/12/2022 at 21:42
 */
public class PacketInHandshake extends PacketIn {

    private int protocol;
    private String address;
    private int port;
    private int nextState;

    @Override
    public void read(NotchianBuffer buffer, ProtocolVersion version) {
        this.protocol = buffer.readVarInt();
        this.address = buffer.readString();
        this.port = buffer.readUnsignedShort();
        this.nextState = buffer.readVarInt();
    }

    public int getProtocol() {
        return this.protocol;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    public int getNextState() {
        return this.nextState;
    }

}
