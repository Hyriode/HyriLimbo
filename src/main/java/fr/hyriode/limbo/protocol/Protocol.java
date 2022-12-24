package fr.hyriode.limbo.protocol;

/**
 * Created by AstFaster
 * on 21/12/2022 at 19:06
 */
public abstract class Protocol {

    protected final ProtocolRegistry registry = new ProtocolRegistry();

    public Protocol() {
        this.registerPackets();
        this.registerHandlers();
    }

    public abstract void registerPackets();

    public abstract void registerHandlers();

    public ProtocolRegistry getRegistry() {
        return this.registry;
    }

}
