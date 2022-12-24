package fr.hyriode.limbo.data;

import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.impl.play.PacketPlayOutTitle;

/**
 * Created by AstFaster
 * on 24/12/2022 at 10:26
 */
public class Title {

    private String title;
    private String subtitle;

    private int fadeIn;
    private int stay;
    private int fadeOut;

    public String getTitle() {
        return this.title;
    }

    public Title withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public Title withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public int getFadeIn() {
        return this.fadeIn;
    }

    public Title withFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public int getStay() {
        return this.stay;
    }

    public Title withStay(int stay) {
        this.stay = stay;
        return this;
    }

    public int getFadeOut() {
        return this.fadeOut;
    }

    public Title withFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public void send(PlayerSession session) {
        session.sendTitle(this);
    }

}
