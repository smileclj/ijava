package nettyx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettyx.handler.NettyWsServerHandler;
import nettyx.util.ChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wana on 2015/11/30.
 */
public class NettyServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChannelManager channelManager;

    private final int port = 9100;
//    private final NettyWsServerHandler handler;

    public NettyServer() {
        channelManager = new ChannelManager();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // server端采用简洁的连写方式，client端采用分段普通写法。
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
//                        ch.pipeline().addLast( new HelloServerHandler());
                    }
                }).option(ChannelOption. SO_KEEPALIVE , true );

//        ChannelFuture f = serverBootstrap.bind(8000).sync();
//        f.channel().closeFuture().sync();
    }


//    public void sendMessage(String toClientId,AbstractMessage message){
//
//    }

}
