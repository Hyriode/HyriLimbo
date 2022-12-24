package fr.hyriode.limbo.protocol.handler.status;

import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusInRequest;

/**
 * Created by AstFaster
 * on 21/12/2022 at 23:18
 */
public class StatusRequestHandler implements PacketInHandler<PacketStatusInRequest> {

    @Override
    public void handle(PlayerSession playerSession, PacketStatusInRequest packet) {
        playerSession.sendStatusResponse();
    }

}
