package fr.hyriode.limbo.protocol.handler.login;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.data.Title;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.player.profile.GameProfile;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginInStart;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayOutTitle;
import fr.hyriode.limbo.util.UUIDUtil;
import fr.hyriode.limbo.world.Difficulty;
import fr.hyriode.limbo.world.Dimension;
import fr.hyriode.limbo.world.GameMode;

import java.util.ArrayList;

/**
 * Created by AstFaster
 * on 22/12/2022 at 10:09
 */
public class LoginStartHandler implements PacketInHandler<PacketLoginInStart> {

    @Override
    public void handle(PlayerSession playerSession, PacketLoginInStart packet) {
        final String name = packet.getName();

        GameProfile profile = playerSession.getProfile();
        if (profile == null) {
            profile = new GameProfile(UUIDUtil.getOffline(name), name, new ArrayList<>());
        } else {
            profile = new GameProfile(profile.id(), name, profile.properties());
        }

        System.out.println(profile.id());

        playerSession.setProfile(profile);
        playerSession.sendLoginSuccess();
        playerSession.setProtocolState(ProtocolState.PLAY);
        playerSession.sendJoinGame(GameMode.SPECTATOR, Dimension.OVERWORLD, Difficulty.PEACEFUL, 1000, "default", false);
        playerSession.sendPosition(0.0D, 0.0D, 0.0D, 0.0f, 0.0f);

        playerSession.sendTitle(new Title().withTitle("§bHyriode")
                .withSubtitle("§fTest")
                .withFadeIn(5)
                .withStay(5 * 20)
                .withFadeOut(5));
        playerSession.sendMessage("§bWelcome on the Limbo!");

        System.out.println(name + " joined the server.");

        HyriLimbo.get().addPlayer(playerSession);
    }

}
