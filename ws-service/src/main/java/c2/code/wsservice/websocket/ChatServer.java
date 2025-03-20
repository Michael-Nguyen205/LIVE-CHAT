package c2.code.wsservice.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ChatServer {

    @Value("${chat.netty.port:8181}")
    private int port;
    @Value("${chat.netty.websocketPath:/ws}")
    private String webSocketPath;

    @Value("${chat.netty.hearbeat.send:true}")
    private boolean sendHeartbeat;
    @Value("${chat.netty.hearbeat.readIdleTime:180}")
    private int readIdleTime;
    @Value("${chat.netty.hearbeat.writeIdleTime:30}")
    private int writeIdleTime;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    @Autowired
    RequestRoutingHandler requestHandler;

    private void startServer() throws InterruptedException {
        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
        } else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
        }
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(true))
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(new WebSocketServerCompressionHandler());
                        pipeline.addLast(new WebSocketServerProtocolHandler(webSocketPath, null, true));
                        if (sendHeartbeat) {
                            pipeline.addLast("idleStateHandler", new IdleStateHandler(readIdleTime, writeIdleTime, 0));
                        }
                        pipeline.addLast(new WebSocketIndexPageHandler(webSocketPath));
                        pipeline.addLast(new HeartBeatHandler());
                        pipeline.addLast(requestHandler);
                    }
                });

        Channel channel = bootstrap.bind(port).sync().channel();
        channel.closeFuture().sync();
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully().sync();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully().sync();
        }
    }

    @PostConstruct()
    public void init() {
        new Thread(() -> {
            try {
                startServer();
            } catch (InterruptedException e) {
                logger.error("CANNOT START NETTY SERVER! STOP THE APPLICATION", e);
                System.exit(1);
            }
        }).start();
    }
}
