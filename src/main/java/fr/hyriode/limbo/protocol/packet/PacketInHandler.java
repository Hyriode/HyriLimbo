package fr.hyriode.limbo.protocol.packet;

import fr.hyriode.limbo.player.PlayerSession;

/**
 * Created by AstFaster
 * on 21/12/2022 at 19:02
 */
public interface PacketInHandler<P extends PacketIn> {

    void handle(PlayerSession playerSession, P packet);

}
