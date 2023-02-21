package fr.hyriode.limbo.network.pipeline;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.protocol.NotchianBuffer;
import fr.hyriode.limbo.protocol.Protocol;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * Created by AstFaster
 * on 21/12/2022 at 17:31
 */
public class PacketCodec extends MessageToMessageCodec<ByteBuf, PacketOut> {

    private Protocol protocol;
    private ProtocolVersion protocolVersion;
    private ProtocolState protocolState;

    public PacketCodec() {
        this.protocolVersion = ProtocolVersion.earliest();
        this.protocol = HyriLimbo.get().getProtocolRepository().getProtocol(this.protocolVersion);
        this.protocolState = ProtocolState.HANDSHAKE;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketOut packet, List<Object> out) {
        final NotchianBuffer buffer = NotchianBuffer.of(ctx.alloc().buffer());

        this.protocol.getRegistry().getPacketId(this.protocolState, packet).ifPresentOrElse(packetId -> {
            buffer.writeVarInt(packetId);
            packet.write(buffer, this.protocolVersion);
            out.add(buffer);
        }, () -> System.err.println("Couldn't find the id of '" + packet.getClass().getSimpleName() + "' packet!"));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (!buf.isReadable() || !ctx.channel().isActive()) {
            return;
        }

        final NotchianBuffer buffer = NotchianBuffer.of(buf);
        final int packetId = buffer.readVarInt();

        this.protocol.getRegistry().getPacketById(this.protocolState, packetId).ifPresent(packet -> {
            packet.read(buffer, this.protocolVersion);
            out.add(packet);
        });
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
         this.protocol = HyriLimbo.get().getProtocolRepository().getProtocol(this.protocolVersion);
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

}
