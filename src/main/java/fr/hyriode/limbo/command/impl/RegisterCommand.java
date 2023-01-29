package fr.hyriode.limbo.command.impl;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.player.IHyriPlayer;
import fr.hyriode.api.player.auth.IHyriAuth;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.command.Command;
import fr.hyriode.limbo.data.Title;
import fr.hyriode.limbo.language.Message;
import fr.hyriode.limbo.player.PlayerSession;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

/**
 * Created by AstFaster
 * on 08/01/2023 at 11:58
 */
public class RegisterCommand extends Command {

    public RegisterCommand() {
        super("register");
    }

    @Override
    public void execute(PlayerSession player, String label, String[] args) {
        if (HyriAPI.get().getLimbo().getType() != HyggLimbo.Type.LOGIN) {
            player.sendMessage(Message.COMMAND_NO_PERMISSION_MESSAGE.asString(player));
            return;
        }

        if (HyriAPI.get().getAuthManager().hasAuth(player.getUniqueId())) { // The player has already his password stored
            player.sendMessage(Message.COMMAND_NO_PERMISSION_MESSAGE.asString(player));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(Message.COMMAND_BAD_USAGE_MESSAGE.asString(player).replace("%usage%", "/register <password> <password>"));
            return;
        }

        final String password = args[0];
        final String confirmPassword = args[1];

        if (!password.equals(confirmPassword)) { // Password and confirmation password doesn't match
            player.sendMessage(Message.REGISTER_PASSWORDS_DONT_MATCH_MESSAGE.asString(player));
            return;
        }

        if (password.length() < 6) { // Password is too short
            player.sendMessage(Message.REGISTER_INVALID_PASSWORD_MESSAGE.asString(player));
            return;
        }

        HyriAPI.get().getAuthManager().newAuth(player.getUniqueId(), password);
        HyriAPI.get().getPlayerManager().createPlayer(false, player.getUniqueId(), player.getName());

        player.sendMessage(Message.REGISTER_SUCCESS_MESSAGE.asString(player));
        player.sendTitle(new Title().withTitle(Message.LOGIN_TITLE.asString(player))
                .withSubtitle(Message.LOGIN_LOGIN_SUBTITLE.asString(player))
                .withStay(Integer.MAX_VALUE));
    }

}
