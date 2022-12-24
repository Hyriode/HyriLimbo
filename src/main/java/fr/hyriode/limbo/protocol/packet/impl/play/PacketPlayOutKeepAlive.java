package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 22/12/2022 at 17:35
 */
public class PacketPlayOutKeepAlive extends PacketOut {

    private final int id;

    public PacketPlayOutKeepAlive(int id) {
        this.id = id;
    }

    @Override
    public void write(NotchianBuffer buffer) {
        buffer.writeVarInt(this.id);
    }

}
