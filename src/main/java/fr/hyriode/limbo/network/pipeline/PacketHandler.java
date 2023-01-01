package fr.hyriode.limbo.network.pipeline;

import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.packet.PacketIn;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * Created by AstFaster
 * on 21/12/2022 at 21:29
 */
public class PacketHandler extends SimpleChannelInboundHandler<PacketIn> {

    private PlayerSession playerSession;

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) {
        final Channel channel = ctx.channel();

        channel.config().setAutoRead(true);

        this.playerSession = new PlayerSession(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketIn msg) {
        this.playerSession.getProtocol().getRegistry().getHandler(msg.getClass()).ifPresent(handler -> handler.handle(this.playerSession, msg.cast()));
    }

    @Override
    public void channelInactive(@NotNull ChannelHandlerContext ctx) {
        this.playerSession.destroy();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.playerSession.destroy();
    }

}
