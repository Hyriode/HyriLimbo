package fr.hyriode.limbo.protocol.packet.impl.play;

import com.google.gson.JsonObject;
import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 22/12/2022 at 19:59
 */
public class PacketPlayOutTitle extends PacketOut {

    private final Action action;

    private String message;

    private int fadeIn;
    private int stay;
    private int fadeOut;

    public PacketPlayOutTitle(int fadeIn, int stay, int fadeOut) {
        this.action = Action.TIMES;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public PacketPlayOutTitle(Action action, String message) {
        if (action != Action.TITLE && action != Action.SUBTITLE) {
            throw new RuntimeException("Invalid action!");
        }
        this.action = action;

        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("text", message);

        this.message = jsonObject.toString();
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        if (version.isMoreOrEqual(ProtocolVersion.V_1_11) && this.action == Action.TIMES) {
            buffer.writeVarInt(3);
        } else {
            buffer.writeEnum(this.action);
        }


        if (this.action == Action.TITLE || this.action == Action.SUBTITLE) {
            buffer.writeString(this.message);
        }

        if (this.action == Action.TIMES) {
            buffer.writeInt(this.fadeIn);
            buffer.writeInt(this.stay);
            buffer.writeInt(this.fadeOut);
        }
    }

    public enum Action {

        TITLE,
        SUBTITLE,
        TIMES,

    }

}
