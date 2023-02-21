package fr.hyriode.limbo.network;

import fr.hyriode.limbo.network.pipeline.FrameDecoder;
import fr.hyriode.limbo.network.pipeline.FrameEncoder;
import fr.hyriode.limbo.network.pipeline.PacketCodec;
import fr.hyriode.limbo.network.pipeline.PacketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.flow.FlowControlHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Created by AstFaster
 * on 21/12/2022 at 13:11
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(@NotNull SocketChannel ch) {
        final ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("timeout", new ReadTimeoutHandler(30));
        pipeline.addLast("frame_decoder", new FrameDecoder());
        pipeline.addLast("length_encoder", new FrameEncoder());
        pipeline.addLast("flow_handler", new FlowControlHandler());
        pipeline.addLast("packet_codec", new PacketCodec());
        pipeline.addLast("packet_handler", new PacketHandler());
    }

}
