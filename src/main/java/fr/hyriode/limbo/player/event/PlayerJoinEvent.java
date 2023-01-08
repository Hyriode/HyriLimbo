package fr.hyriode.limbo.player.event;

import fr.hyriode.api.event.HyriEvent;
import fr.hyriode.limbo.player.PlayerSession;

/**
 * Created by AstFaster
 * on 08/01/2023 at 11:22
 */
public class PlayerJoinEvent extends HyriEvent {

    private final PlayerSession player;

    public PlayerJoinEvent(PlayerSession player) {
        this.player = player;
    }

    public PlayerSession getPlayer() {
        return this.player;
    }

}
