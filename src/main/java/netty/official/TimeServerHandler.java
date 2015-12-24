package netty.official;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ExecutorService pool = Executors.newFixedThreadPool(8);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf buf = (ByteBuf) msg;
//
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, Charset.forName("UTF-8")).substring(0,req.length - System.getProperty("line.separator").length());
        pool.execute(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
                String body = (String) msg;
//        System.out.println("the time server receive order:" + body + ";the counter is :" + ++counter);
                String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                currentTime = currentTime + System.getProperty("line.separator");
                ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(resp);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}