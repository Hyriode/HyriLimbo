package fr.hyriode.limbo.world;

/**
 * Created by AstFaster
 * on 22/12/2022 at 11:03
 */
public enum Dimension {

    OVERWORLD((byte) 0)

    ;

    private final byte id;

    Dimension(byte id) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }

}
