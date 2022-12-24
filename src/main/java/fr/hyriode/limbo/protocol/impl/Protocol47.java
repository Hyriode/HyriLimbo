package fr.hyriode.limbo.protocol.impl;

import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.play.*;

/**
 * Created by AstFaster
 * on 21/12/2022 at 21:38
 */
public class Protocol47 extends ProtocolCommon {

    @Override
    public void registerPackets() {
        super.registerPackets();

        this.registry.registerOut(ProtocolState.PLAY, 0x00, PacketPlayOutKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x01, PacketPlayOutJoinGame.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x02, PacketPlayOutMessage.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x08, PacketPlayOutPositionAndLook.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x40, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x45, PacketPlayOutTitle.class);
    }

}
