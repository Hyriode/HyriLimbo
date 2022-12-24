package fr.hyriode.limbo.protocol.packet.impl.login;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

import java.util.UUID;

/**
 * Created by AstFaster
 * on 22/12/2022 at 10:04
 */
public class PacketLoginOutSuccess extends PacketOut {

    private final UUID uuid;
    private final String name;

    public PacketLoginOutSuccess(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        buffer.writeString(this.uuid.toString());
        buffer.writeString(this.name);
    }

}
