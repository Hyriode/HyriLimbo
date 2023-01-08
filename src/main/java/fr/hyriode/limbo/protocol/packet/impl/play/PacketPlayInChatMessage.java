package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 08/01/2023 at 10:52
 */
public class PacketPlayInChatMessage extends PacketIn {

    private String message;

    @Override
    public void read(NotchianBuffer buffer, ProtocolVersion version) {
        this.message = buffer.readString();
    }

    public String getMessage() {
        return this.message;
    }

}
