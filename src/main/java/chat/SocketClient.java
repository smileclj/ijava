package chat;

import chat.handler.SocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SocketClientHandler());
                        }
                    });

            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("客户端启动");
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放NIO线程组
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8888;
        new SocketClient().connect(host, port);
    }
}