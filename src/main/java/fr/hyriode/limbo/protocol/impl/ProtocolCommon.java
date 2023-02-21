package fr.hyriode.limbo.protocol.impl;

import fr.hyriode.limbo.protocol.Protocol;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.handler.HandshakeHandler;
import fr.hyriode.limbo.protocol.handler.login.LoginStartHandler;
import fr.hyriode.limbo.protocol.handler.play.ChatHandler;
import fr.hyriode.limbo.protocol.handler.play.KeepAliveHandler;
import fr.hyriode.limbo.protocol.handler.status.PingHandler;
import fr.hyriode.limbo.protocol.handler.status.StatusRequestHandler;
import fr.hyriode.limbo.protocol.packet.impl.PacketInHandshake;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginInStart;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginOutSuccess;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayInChatMessage;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayInKeepAlive;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusInPing;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusInRequest;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutPong;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutResponse;

/**
 * Created by AstFaster
 * on 24/12/2022 at 14:15
 */
public abstract class ProtocolCommon extends Protocol {

    @Override
    public void registerPackets() {
        this.registry.registerIn(ProtocolState.HANDSHAKE, 0x00, PacketInHandshake.class);

        this.registry.registerIn(ProtocolState.STATUS, 0x00, PacketStatusInRequest.class);
        this.registry.registerIn(ProtocolState.STATUS, 0x01, PacketStatusInPing.class);
        this.registry.registerOut(ProtocolState.STATUS, 0x00, PacketStatusOutResponse.class);
        this.registry.registerOut(ProtocolState.STATUS, 0x01, PacketStatusOutPong.class);

        this.registry.registerIn(ProtocolState.LOGIN, 0x00, PacketLoginInStart.class);
        this.registry.registerOut(ProtocolState.LOGIN, 0x00, PacketOutDisconnect.class);
        this.registry.registerOut(ProtocolState.LOGIN, 0x02, PacketLoginOutSuccess.class);
    }

    @Override
    public void registerHandlers() {
        this.registry.registerHandler(PacketInHandshake.class, new HandshakeHandler());
        this.registry.registerHandler(PacketStatusInRequest.class, new StatusRequestHandler());
        this.registry.registerHandler(PacketStatusInPing.class, new PingHandler());
        this.registry.registerHandler(PacketLoginInStart.class, new LoginStartHandler());
        this.registry.registerHandler(PacketPlayInKeepAlive.class, new KeepAliveHandler());
        this.registry.registerHandler(PacketPlayInChatMessage.class, new ChatHandler());
    }

}
