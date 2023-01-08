package fr.hyriode.limbo.protocol.packet.impl.play;

import com.google.gson.JsonObject;
import fr.hyriode.api.color.HyriChatColor;
import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 24/12/2022 at 10:34
 */
public class PacketPlayOutMessage extends PacketOut {

    private final ChatPosition position;
    private final String message;

    public PacketPlayOutMessage(ChatPosition position, String message) {
        this.position = position;

        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("text", HyriChatColor.translateAlternateColorCodes('&', message));

        this.message = jsonObject.toString();
    }

    @Override
    public void write(NotchianBuffer buffer, ProtocolVersion version) {
        buffer.writeString(this.message);

        if (version.isMoreOrEqual(ProtocolVersion.V_1_19_2)) {
            buffer.writeBoolean(this.position == ChatPosition.ACTION_BAR);
        } else if (version == ProtocolVersion.V_1_19) {
            buffer.writeVarInt(this.position.getId());
        } else {
            buffer.writeByte(this.position.getId());
        }
    }

    public enum ChatPosition {

        CHAT(0),
        SYSTEM(1),
        ACTION_BAR(2);

        private final int id;

        ChatPosition(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

    }

}
