package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by AstFaster
 * on 22/12/2022 at 13:14
 */
public class PacketPlayOutPositionAndLook extends PacketOut {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public PacketPlayOutPositionAndLook(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
        buffer.writeFloat(this.yaw);
        buffer.writeFloat(this.pitch);

        if (version.isMoreOrEqual(ProtocolVersion.V_1_8)) {
            buffer.writeByte(0x08);
        }

        if (version.isMoreOrEqual(ProtocolVersion.V_1_9)) {
            buffer.writeVarInt(ThreadLocalRandom.current().nextInt());
        }
    }

}
