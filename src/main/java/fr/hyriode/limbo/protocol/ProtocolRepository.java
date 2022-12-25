package fr.hyriode.limbo.protocol;

import fr.hyriode.limbo.protocol.impl.*;

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
        this.registerProtocol(ProtocolVersion.V_1_9_1, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_9_2, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_9_4, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_10, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_11, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_11_2, new Protocol107());
        this.registerProtocol(ProtocolVersion.V_1_12, new Protocol335());
        this.registerProtocol(ProtocolVersion.V_1_12_1, new Protocol338());
        this.registerProtocol(ProtocolVersion.V_1_12_2, new Protocol338());
        this.registerProtocol(ProtocolVersion.V_1_13, new Protocol393());
        this.registerProtocol(ProtocolVersion.V_1_13_1, new Protocol393());
        this.registerProtocol(ProtocolVersion.V_1_13_2, new Protocol393());
        this.registerProtocol(ProtocolVersion.V_1_14, new Protocol477());
        this.registerProtocol(ProtocolVersion.V_1_14_1, new Protocol477());
        this.registerProtocol(ProtocolVersion.V_1_14_2, new Protocol477());
        this.registerProtocol(ProtocolVersion.V_1_14_3, new Protocol477());
        this.registerProtocol(ProtocolVersion.V_1_15, new Protocol573());
        this.registerProtocol(ProtocolVersion.V_1_15_1, new Protocol573());
        this.registerProtocol(ProtocolVersion.V_1_15_2, new Protocol573());
        this.registerProtocol(ProtocolVersion.V_1_16, new Protocol735());
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
