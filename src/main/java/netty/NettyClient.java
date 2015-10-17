package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by lao on 2015/9/23.
 */
public class NettyClient {
    public void startClient(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1",8000).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        }catch(InterruptedException e){
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    private class HelloClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Hello,I'm Client!");
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.startClient();
    }
}
