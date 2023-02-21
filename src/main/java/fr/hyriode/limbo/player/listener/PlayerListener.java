package fr.hyriode.limbo.player.listener;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.event.HyriEventHandler;
import fr.hyriode.api.player.IHyriPlayer;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.data.Title;
import fr.hyriode.limbo.language.Message;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.player.event.PlayerJoinEvent;

/**
 * Created by AstFaster
 * on 08/01/2023 at 11:23
 */
public class PlayerListener {

    @HyriEventHandler
    public void onJoin(PlayerJoinEvent event) {
        final PlayerSession player = event.getPlayer();
        final HyggLimbo.Type limboType = HyriAPI.get().getLimbo().getType();
        final IHyriPlayer account = IHyriPlayer.get(player.getUniqueId());

        if (account != null && account.getAuth().isPremium() && limboType == HyggLimbo.Type.LOGIN) { // Why is he on a login limbo?
            HyriAPI.get().getLobbyAPI().sendPlayerToLobby(player.getUniqueId());
            return;
        }

        switch (limboType) {
            case AFK -> player.sendTitle(new Title().withTitle(Message.AFK_TITLE.asString(account))
                    .withSubtitle(Message.AFK_SUBTITLE.asString(account))
                    .withStay(Integer.MAX_VALUE));
            case LOGIN -> player.sendTitle(new Title().withTitle(Message.LOGIN_TITLE.asString(account))
                    .withSubtitle(((account != null && account.getAuth().getHash() != null) ? Message.LOGIN_LOGIN_SUBTITLE : Message.LOGIN_REGISTER_SUBTITLE).asString(account))
                    .withStay(Integer.MAX_VALUE));
        }
    }

}
