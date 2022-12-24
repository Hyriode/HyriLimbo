package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 22/12/2022 at 17:35
 */
public class PacketPlayOutKeepAlive extends PacketOut {

    private final long id;

    public PacketPlayOutKeepAlive(long id) {
        this.id = id;
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        if (version.isMoreOrEqual(ProtocolVersion.V_1_12_2)) {
            buffer.writeLong(this.id);
        } else {
            buffer.writeVarInt((int) this.id);
        }
    }

}
