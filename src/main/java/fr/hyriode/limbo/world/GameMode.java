package fr.hyriode.limbo.world;

/**
 * Created by AstFaster
 * on 22/12/2022 at 10:51
 */
public enum GameMode {

    SURVIVAL((byte) 0),
    CREATIVE((byte) 1),
    ADVENTURE((byte) 2),
    SPECTATOR((byte) 3);

    private final byte id;

    GameMode(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

}
