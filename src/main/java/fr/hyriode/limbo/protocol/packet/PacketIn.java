package fr.hyriode.limbo.protocol.packet;

import fr.hyriode.limbo.protocol.NotchianBuffer;

/**
 * Created by AstFaster
 * on 21/12/2022 at 18:56
 */
public abstract class PacketIn extends Packet {

    public abstract void read(NotchianBuffer buffer);

}
