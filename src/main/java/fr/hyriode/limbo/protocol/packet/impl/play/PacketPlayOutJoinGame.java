package fr.hyriode.limbo.protocol.packet.impl.play;

import fr.hyriode.limbo.world.GameMode;
import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketOut;
import fr.hyriode.limbo.world.Difficulty;
import fr.hyriode.limbo.world.Dimension;

/**
 * Created by AstFaster
 * on 22/12/2022 at 11:00
 */
public class PacketPlayOutJoinGame extends PacketOut {

    private final int entityId;
    private final GameMode gameMode;
    private final Dimension dimension;
    private final Difficulty difficulty;
    private final int maxPlayers;
    private final String levelType;
    private final boolean debugInfo;

    public PacketPlayOutJoinGame(int entityId, GameMode gameMode, Dimension dimension, Difficulty difficulty, int maxPlayers, String levelType, boolean debugInfo) {
        this.entityId = entityId;
        this.gameMode = gameMode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.debugInfo = debugInfo;
    }

    @Override
    public void write(NotchianBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.gameMode.getId());
        buffer.writeByte(this.dimension.getId());
        buffer.writeByte(this.difficulty.getId());
        buffer.writeByte(this.maxPlayers);
        buffer.writeString(this.levelType);
        buffer.writeBoolean(this.debugInfo);
    }

}
