package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 22/12/2022 at 17:37
 */
public class PacketPlayInKeepAlive extends PacketIn {

    private int id;

    @Override
    public void read(NotchianBuffer buffer, ProtocolVersion version) {
        this.id = buffer.readVarInt();
    }

    public int getId() {
        return this.id;
    }

}
