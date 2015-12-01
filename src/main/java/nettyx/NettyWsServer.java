package nettyx;

import chat.handler.DispatchHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import nettyx.handler.NettyWsServerHandler;
import nettyx.util.ChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wana on 2015/11/30.
 */
public class NettyWsServer extends Thread {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChannelManager channelManager;
    private ServerBootstrap b;
    private NettyWsServerHandler handler;

    private final int serverPort;
    private final String serverName;

    public NettyWsServer(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.channelManager = new ChannelManager();
        this.handler = new NettyWsServerHandler(serverName,serverPort,channelManager);
        this.b = new ServerBootstrap();

        EventLoopGroup bossGrouop = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(bossGrouop, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("http-codec", new HttpServerCodec());
                        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                        pipeline.addLast(handler);
                    }
                });
    }

    @Override
    public void run() {
        try {
            Channel channel = b.bind(serverPort).sync().channel();
            logger.info("[" + serverName + "] -> Listening on port " + serverPort);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("NettyWsServer bind failed!!!", e);
        }
    }

    public static void main(String[] args) {
        new NettyWsServer("wsServer",9200).start();
    }
}
