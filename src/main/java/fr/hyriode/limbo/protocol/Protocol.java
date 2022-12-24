package fr.hyriode.limbo.protocol;

/**
 * Created by AstFaster
 * on 21/12/2022 at 19:06
 */
public abstract class Protocol {

    protected ProtocolRegistry registry = new ProtocolRegistry();

    public ProtocolRegistry getRegistry() {
        return this.registry;
    }

}
