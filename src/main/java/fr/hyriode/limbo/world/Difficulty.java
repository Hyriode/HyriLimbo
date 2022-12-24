package fr.hyriode.limbo.world;

/**
 * Created by AstFaster
 * on 22/12/2022 at 10:54
 */
public enum Difficulty {

    PEACEFUL((byte) 0),
    EASY((byte) 1),
    NORMAL((byte) 2),
    HARD((byte) 3);

    private final byte id;

    Difficulty(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

}
