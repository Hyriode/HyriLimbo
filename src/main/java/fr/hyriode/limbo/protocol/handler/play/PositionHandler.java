package fr.hyriode.limbo.protocol.handler.play;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayInPosition;
import fr.hyriode.limbo.world.Location;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by AstFaster
 * on 08/04/2023 at 21:19
 */
public class PositionHandler implements PacketInHandler<PacketPlayInPosition> {

    private final Set<UUID> sendingToLobby = ConcurrentHashMap.newKeySet();

    @Override
    public void handle(PlayerSession playerSession, PacketPlayInPosition packet) {
        if (HyriAPI.get().getLimbo().getType() == HyggLimbo.Type.AFK) {
            final UUID playerId = playerSession.getUniqueId();
            final Location lastLocation = playerSession.getLocation();

            playerSession.setLocation(new Location(packet.getX(), packet.getY(), packet.getZ()));

            if (!this.sendingToLobby.contains(playerId) && lastLocation != null && !playerSession.getLocation().equals(lastLocation)) {
                this.sendingToLobby.add(playerId);

                playerSession.resetTitle();

                HyriAPI.get().getLobbyAPI().sendPlayerToLobby(playerSession.getUniqueId());
            }
        }
    }

}
