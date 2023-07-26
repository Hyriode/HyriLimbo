package fr.hyriode.limbo.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.function.Supplier;

/**
 * Created by AstFaster
 * on 21/12/2022 at 12:35
 */
public class Server {

    private EventLoopGroup bossGroup;

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        final Transport transport = Transport.get();

        this.bossGroup = transport.newEventLoop();

        final ServerBootstrap bootstrap = new ServerBootstrap()
                .group(this.bossGroup)
                .channel(transport.channel())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerInitializer());

        try {
            final Channel channel = bootstrap.bind("0.0.0.0", this.port).sync().channel();

            System.out.println("Listening incoming connections on " + this.port + "...");

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.err.println("Failed to bind to port! Make sure that no other applications are using the port!");
            System.err.println("Exception: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void stop() {
        this.bossGroup.shutdownGracefully();
    }

    private enum Transport {

        EPOLL(0, () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty IO Thread #%1$d").build()), EpollServerSocketChannel.class, Epoll::isAvailable),
        NIO(1, () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty IO Thread #%1$d").build()), NioServerSocketChannel.class, () -> true);

        private final int priority;

        private final Supplier<EventLoopGroup> eventLoop;
        private final Class<? extends ServerChannel> channel;
        private final Supplier<Boolean> availability;

        Transport(int priority, Supplier<EventLoopGroup> eventLoop, Class<? extends ServerChannel> channel, Supplier<Boolean> availability) {
            this.priority = priority;
            this.eventLoop = eventLoop;
            this.channel = channel;
            this.availability = availability;
        }

        public int priority() {
            return this.priority;
        }

        public EventLoopGroup newEventLoop() {
            return this.eventLoop.get();
        }

        public Class<? extends ServerChannel> channel() {
            return this.channel;
        }

        public boolean isAvailable() {
            return this.availability.get();
        }

        public static Transport get() {
            Transport result = null;
            for (Transport transport : values()) {
                if (transport.isAvailable() && (result == null || transport.priority() < result.priority())) {
                    result = transport;
                }
            }
            return result;
        }

    }

}
