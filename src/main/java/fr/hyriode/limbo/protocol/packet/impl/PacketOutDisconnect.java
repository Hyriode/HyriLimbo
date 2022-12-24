package fr.hyriode.limbo.protocol.packet.impl;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 21/12/2022 at 22:59
 */
public class PacketOutDisconnect extends PacketOut {

    private final String reason;

    public PacketOutDisconnect(String reason) {
        this.reason = "{\"text\": \"" + reason + "\"}";
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        buffer.writeString(this.reason);
    }

}
