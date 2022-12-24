package fr.hyriode.limbo.protocol.packet.impl.status;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 21/12/2022 at 23:06
 */
public class PacketStatusInPing extends PacketIn {

    private long id;

    @Override
    public void read(NotchianBuffer buffer) {
        this.id = buffer.readLong();
    }

    public long getId() {
        return this.id;
    }

}
