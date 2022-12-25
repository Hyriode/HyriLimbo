package fr.hyriode.limbo.protocol.impl;

import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.play.*;

/**
 * Created by AstFaster
 * on 24/12/2022 at 16:04
 */
public class Protocol393 extends ProtocolCommon {

    @Override
    public void registerPackets() {
        super.registerPackets();

        this.registry.registerIn(ProtocolState.PLAY, 0x0E, PacketPlayInKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x21, PacketPlayOutKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x25, PacketPlayOutJoinGame.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x0E, PacketPlayOutMessage.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x32, PacketPlayOutPositionAndLook.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x1B, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x4B, PacketPlayOutTitle.class);
    }

}
