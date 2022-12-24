package fr.hyriode.limbo.protocol.impl;

import fr.hyriode.limbo.protocol.Protocol;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.handler.HandshakeHandler;
import fr.hyriode.limbo.protocol.handler.login.LoginStartHandler;
import fr.hyriode.limbo.protocol.handler.play.KeepAliveHandler;
import fr.hyriode.limbo.protocol.handler.status.PingHandler;
import fr.hyriode.limbo.protocol.handler.status.StatusRequestHandler;
import fr.hyriode.limbo.protocol.packet.impl.PacketInHandshake;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginInStart;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginOutSuccess;
import fr.hyriode.limbo.protocol.packet.impl.play.*;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusInPing;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusInRequest;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutPong;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutResponse;

/**
 * Created by AstFaster
 * on 21/12/2022 at 21:38
 */
public class Protocol47 extends Protocol {

    public Protocol47() {
        // Register incoming packets
        this.registry.registerIn(ProtocolState.HANDSHAKE, 0x00, PacketInHandshake.class);

        this.registry.registerIn(ProtocolState.STATUS, 0x00, PacketStatusInRequest.class);
        this.registry.registerIn(ProtocolState.STATUS, 0x01, PacketStatusInPing.class);
        this.registry.registerOut(ProtocolState.STATUS, 0x00, PacketStatusOutResponse.class);
        this.registry.registerOut(ProtocolState.STATUS, 0x01, PacketStatusOutPong.class);

        this.registry.registerIn(ProtocolState.LOGIN, 0x00, PacketLoginInStart.class);
        this.registry.registerOut(ProtocolState.LOGIN, 0x00, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.LOGIN, 0x02, PacketLoginOutSuccess.class);

        this.registry.registerIn(ProtocolState.PLAY, 0x00, PacketPlayInKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x00, PacketPlayOutKeepAlive.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x01, PacketPlayOutJoinGame.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x02, PacketPlayOutMessage.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x08, PacketPlayOutPositionAndLook.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x40, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.PLAY, 0x45, PacketPlayOutTitle.class);

        // Register handlers
        this.registry.registerHandler(PacketInHandshake.class, new HandshakeHandler());
        this.registry.registerHandler(PacketStatusInRequest.class, new StatusRequestHandler());
        this.registry.registerHandler(PacketStatusInPing.class, new PingHandler());
        this.registry.registerHandler(PacketLoginInStart.class, new LoginStartHandler());
        this.registry.registerHandler(PacketPlayInKeepAlive.class, new KeepAliveHandler());
    }

}
