package chat.util;

import java.util.Iterator;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;

public class QueueUtil {

    public ConcurrentHashMapV8<ChannelHandlerContext, String> maps = new ConcurrentHashMapV8<ChannelHandlerContext, String>(
            0);

    private QueueUtil() {
    }

    // 一个延迟实例化的内部类的单例模式
    // 一个内部类的容器，调用getInstance时，JVM加载这个类
    private static final class SingletonHolder {
        static final QueueUtil singleton = new QueueUtil();
    }

    public static QueueUtil getInstance() {
        return SingletonHolder.singleton;
    }

    public void addSocket(ChannelHandlerContext socket, String ID) {
        maps.put(socket, ID);
    }

    public void removeSocket(ChannelHandlerContext ctx) {
        maps.remove(ctx);
    }

    public int getSize() {
        return maps.size();
    }

    public void broadMsg(String msg) {
        if (!maps.isEmpty()) {
            Iterator it = maps.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                ChannelHandlerContext ctx = (ChannelHandlerContext) entry.getKey();
                String s = (String) entry.getValue();
                if (s.length() == 24) {
                    ctx.writeAndFlush(new TextWebSocketFrame(msg));
                } else {
                    ctx.writeAndFlush(msg + "\n");
                }
            }
        }
    }

}
