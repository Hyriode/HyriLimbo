package fr.hyriode.limbo.protocol.packet.impl.status;

import com.google.gson.JsonObject;
import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketOut;

/**
 * Created by AstFaster
 * on 21/12/2022 at 23:11
 */
public class PacketStatusOutResponse extends PacketOut {

    private final String response;

    public PacketStatusOutResponse(String name, int protocol, int slots, int totalPlayers, String motd, String favicon) {
        final JsonObject payload = new JsonObject();
        final JsonObject version = new JsonObject();
        final JsonObject players = new JsonObject();
        final JsonObject description = new JsonObject();

        version.addProperty("name", name);
        version.addProperty("protocol", protocol);

        players.addProperty("max", slots);
        players.addProperty("online", totalPlayers);

        description.addProperty("text", motd);

        payload.add("version", version);
        payload.add("players", players);
        payload.add("description", description);
        payload.addProperty("favicon", "data:image/png;base64," + favicon);
        payload.addProperty("previewsChat", true);
        payload.addProperty("enforcesSecureChat", true);

        this.response = payload.toString();
    }

    @Override
    public void write(NotchianBuffer buffer) {
        buffer.writeString(this.response);
    }

}
