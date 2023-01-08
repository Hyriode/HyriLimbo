package fr.hyriode.limbo.protocol.handler.play;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayInChatMessage;

/**
 * Created by AstFaster
 * on 08/01/2023 at 10:59
 */
public class ChatHandler implements PacketInHandler<PacketPlayInChatMessage> {

    @Override
    public void handle(PlayerSession playerSession, PacketPlayInChatMessage packet) {
        final String message = packet.getMessage();

        // Check whether the message is a command
        if (message.trim().startsWith("/")) {
            HyriLimbo.get().getCommandManager().process(playerSession, message);
            return;
        }
    }

}
