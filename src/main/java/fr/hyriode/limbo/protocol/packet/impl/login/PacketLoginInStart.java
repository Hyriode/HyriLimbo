package fr.hyriode.limbo.protocol.packet.impl.login;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.packet.PacketIn;

/**
 * Created by AstFaster
 * on 22/12/2022 at 09:39
 */
public class PacketLoginInStart extends PacketIn {

    private String name;

    @Override
    public void read(NotchianBuffer buffer) {
        this.name = buffer.readString();
    }

    public String getName() {
        return this.name;
    }

}
