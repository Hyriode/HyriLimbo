package fr.hyriode.limbo.protocol.handler;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.player.profile.GameProfile;
import fr.hyriode.limbo.protocol.Protocol;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.PacketInHandshake;
import fr.hyriode.limbo.util.UUIDUtil;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by AstFaster
 * on 21/12/2022 at 22:43
 */
public class HandshakeHandler implements PacketInHandler<PacketInHandshake> {

    @Override
    public void handle(PlayerSession playerSession, PacketInHandshake packet) {
        final ProtocolVersion protocolVersion = ProtocolVersion.fromId(packet.getProtocol());

        if (protocolVersion == null) { // Protocol doesn't exist
            playerSession.disconnect("Invalid version!");
            return;
        }

        final Protocol protocol = HyriLimbo.get().getProtocolRepository().getProtocol(protocolVersion);

        if (protocol == null) { // Protocol is not implemented yet
            playerSession.disconnect("Invalid version!");
            return;
        }

        playerSession.setProtocol(protocol);
        playerSession.setProtocolVersion(protocolVersion);

        switch (packet.getNextState()) {
            case 1 -> playerSession.setProtocolState(ProtocolState.STATUS);
            case 2 -> playerSession.setProtocolState(ProtocolState.LOGIN);
            default -> {
                playerSession.disconnect("Invalid handshake next state!");

                HyriLimbo.get().getLogger().log(Level.WARNING, "Received invalid handshake next state: " + packet.getNextState() + "!");
            }
        }

        // Handle BungeeCord information forwarding
        final String[] split = packet.getAddress().split("\00");

        if (split.length == 3 || split.length == 4) {
            playerSession.setProfile(new GameProfile(UUIDUtil.fromString(split[2]), null, new ArrayList<>()));
        }
    }

}
