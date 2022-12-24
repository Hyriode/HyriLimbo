package fr.hyriode.limbo.protocol;

import fr.hyriode.limbo.protocol.impl.Protocol107;
import fr.hyriode.limbo.protocol.impl.Protocol108;
import fr.hyriode.limbo.protocol.impl.Protocol47;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AstFaster
 * on 21/12/2022 at 19:07
 */
public class ProtocolRepository {

    private final Map<ProtocolVersion, Protocol> protocols = new HashMap<>();

    public ProtocolRepository() {
        this.registerProtocol(ProtocolVersion.V_1_8, new Protocol47());
        this.registerProtocol(ProtocolVersion.V_1_9, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_9_1, new Protocol108());
    }

    public void registerProtocol(ProtocolVersion protocolVersion, Protocol protocol) {
        this.protocols.put(protocolVersion, protocol);

        System.out.println("Registered '" + protocolVersion.name() + "' protocol.");
    }

    public void unregisterProtocol(ProtocolVersion protocolVersion) {
        this.protocols.remove(protocolVersion);
    }

    public Protocol getProtocol(ProtocolVersion protocolVersion) {
        return this.protocols.get(protocolVersion);
    }

}
