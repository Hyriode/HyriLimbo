package fr.hyriode.limbo.network.pipeline;

import fr.hyriode.limbo.protocol.NotchianBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * Created by AstFaster
 * on 21/12/2022 at 13:50
 */
public class FrameDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // Dispose of unreadable buffers
        if (!in.isReadable()) {
            return;
        }

        int origReaderIndex = in.readerIndex();

        // We might need to read a few bytes until we get some data
        for (int i = 0; i < 3; i++) {
            if (!in.isReadable()) {
                in.readerIndex(origReaderIndex);
                return;
            }

            byte read = in.readByte();
            if (read >= 0) {
                in.readerIndex(origReaderIndex);
                int packetLength = NotchianBuffer.of(in).readVarInt();

                if (in.readableBytes() >= packetLength) {
                    out.add(in.readRetainedSlice(packetLength));
                } else {
                    in.readerIndex(origReaderIndex);
                }

                return;
            }
        }

        throw new CorruptedFrameException("VarInt too big");
    }

}
