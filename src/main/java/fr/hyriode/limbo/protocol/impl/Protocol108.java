package fr.hyriode.limbo.protocol.impl;

import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.play.*;

/**
 * Created by AstFaster
 * on 24/12/2022 at 14:08
 */
public class Protocol108 extends ProtocolCommon {

    @Override
    public void registerPackets() {
        super.registerPackets();

        this.registry.registerIn(ProtocolState.PLAY, 0x0B, PacketPlayInKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x1F, PacketPlayOutKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x23, PacketPlayOutJoinGame.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x0F, PacketPlayOutMessage.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x2E, PacketPlayOutPositionAndLook.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x1A, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x45, PacketPlayOutTitle.class);
    }

}
