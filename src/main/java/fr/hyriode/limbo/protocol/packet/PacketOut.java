package fr.hyriode.limbo.protocol.packet;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;

/**
 * Created by AstFaster
 * on 21/12/2022 at 18:56
 */
public abstract class PacketOut extends Packet {

    public abstract void write(NotchianBuffer buffer, ProtocolVersion version);

}
