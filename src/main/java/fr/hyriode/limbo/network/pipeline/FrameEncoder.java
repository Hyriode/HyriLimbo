package fr.hyriode.limbo.network.pipeline;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by AstFaster
 * on 21/12/2022 at 17:16
 */
public class FrameEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        // Varints are never longer than 5 bytes
        final ByteBuf lengthBuf = ctx.alloc().heapBuffer(5);

        NotchianBuffer.of(lengthBuf).writeVarInt(msg.readableBytes());

        out.add(lengthBuf);
        out.add(msg.retain());
    }

}
