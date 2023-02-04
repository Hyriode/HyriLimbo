package fr.hyriode.limbo.command.impl;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.player.IHyriPlayer;
import fr.hyriode.api.player.auth.IHyriAuth;
import fr.hyriode.api.player.event.PlayerJoinNetworkEvent;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.command.Command;
import fr.hyriode.limbo.data.Title;
import fr.hyriode.limbo.language.Message;
import fr.hyriode.limbo.player.PlayerSession;

import java.util.ArrayList;

/**
 * Created by AstFaster
 * on 08/01/2023 at 11:58
 */
public class LoginCommand extends Command {

    public LoginCommand() {
        super("login");
    }

    @Override
    public void execute(PlayerSession player, String label, String[] args) {
        if (HyriAPI.get().getLimbo().getType() != HyggLimbo.Type.LOGIN) {
            player.sendMessage(Message.COMMAND_NO_PERMISSION_MESSAGE.asString(player));
            return;
        }

        if (!HyriAPI.get().getAuthManager().hasAuth(player.getUniqueId())) { // The player is not registered!
            player.sendMessage(Message.COMMAND_NO_PERMISSION_MESSAGE.asString(player));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(Message.COMMAND_BAD_USAGE_MESSAGE.asString(player).replace("%usage%", "/login <password>"));
            return;
        }

        final String password = args[0];
        final IHyriAuth auth = HyriAPI.get().getAuthManager().getAuth(player.getUniqueId());

        if (auth != null) {
            if (auth.authenticate(password)) { // Check whether password is good
                player.sendMessage(Message.LOGIN_SUCCESS_MESSAGE.asString(player));
                player.resetTitle();

                HyriAPI.get().getNetworkManager().getEventBus().publishAsync(new PlayerJoinNetworkEvent(player.getUniqueId()));
                HyriAPI.get().getLobbyAPI().sendPlayerToLobby(player.getUniqueId());
                return;
            }

            player.sendMessage(Message.LOGIN_INVALID_PASSWORD_MESSAGE.asString(player));
        }
    }

}
