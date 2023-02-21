package fr.hyriode.limbo.command.impl;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.command.Command;
import fr.hyriode.limbo.language.Message;
import fr.hyriode.limbo.player.PlayerSession;

/**
 * Created by AstFaster
 * on 08/01/2023 at 11:16
 */
public class LobbyCommand extends Command {

    public LobbyCommand() {
        super("lobby", "l", "hub");
    }

    @Override
    public void execute(PlayerSession player, String label, String[] args) {
        if (HyriAPI.get().getLimbo().getType() != HyggLimbo.Type.AFK) {
            player.sendMessage(Message.COMMAND_NO_PERMISSION_MESSAGE.asString(player));
            return;
        }

        player.resetTitle();

        HyriAPI.get().getLobbyAPI().sendPlayerToLobby(player.getUniqueId());
    }

}
