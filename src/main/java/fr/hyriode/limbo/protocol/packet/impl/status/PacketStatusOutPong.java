package fr.hyriode.limbo.protocol.packet.impl.status;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 21/12/2022 at 23:08
 */
public class PacketStatusOutPong extends PacketOut {

    private final long id;

    public PacketStatusOutPong(long id) {
        this.id = id;
    }

    @Override
    public void write(NotchianBuffer buffer) {
        buffer.writeLong(this.id);
    }

}
