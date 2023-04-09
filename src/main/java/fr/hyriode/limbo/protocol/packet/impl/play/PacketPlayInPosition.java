package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 08/04/2023 at 20:18
 */
public class PacketPlayInPosition extends PacketIn {

    private double x;
    private double y;
    private double z;
    private boolean onGround;

    @Override
    public void read(NotchianBuffer buffer, ProtocolVersion version) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.onGround = buffer.readBoolean();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

}
