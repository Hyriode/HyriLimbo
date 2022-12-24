package fr.hyriode.limbo.protocol.handler.play;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.PacketInHandler;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayInKeepAlive;

/**
 * Created by AstFaster
 * on 22/12/2022 at 17:38
 */
public class KeepAliveHandler implements PacketInHandler<PacketPlayInKeepAlive> {

    public KeepAliveHandler() {
        final Thread thread = new Thread(() -> {
            while (true) {
                for (PlayerSession playerSession : HyriLimbo.get().getPlayers()) {
                    if (playerSession.getLastKeepAlive() != -1) {
                        final long supposedLastTime = System.currentTimeMillis() - 30 * 1000L; // 30 seconds

                        if (playerSession.getLastKeepAlive() < supposedLastTime) {
                            playerSession.disconnect("Timed out");
                            return;
                        }
                    }

                    playerSession.sendKeepAlive();
                }

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void handle(PlayerSession playerSession, PacketPlayInKeepAlive packet) {
        final int keepAliveId = packet.getId();

        if (playerSession.getKeepAliveId() == keepAliveId) {
            playerSession.setLastKeepAlive(System.currentTimeMillis());
        }
    }

}
