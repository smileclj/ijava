package nettyx.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import nettyx.entity.CommonUser;
import nettyx.handler.NettyServerHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wana on 2015/11/30.
 */
public class ChannelManager {
    private ConcurrentHashMap<String, ChannelHandlerContext> ctxs = new ConcurrentHashMap<String, ChannelHandlerContext>();
//    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //netty会自动生成channelId  目前通道ID采用自己生成的

    public void addChannelContext(ChannelHandlerContext ctx, CommonUser user) {
        ctx.attr(NettyServerHandler.CHANNEL_KEY_CLIENT_ID).set(user.getId());
        ctx.attr(NettyServerHandler.CHANNEL_KEY_CLIENT_INFO).set(user);
        ctxs.put(user.getId(), ctx);
    }

    public boolean isUserAlreadyLogin(String clientId) {
        return ctxs.get(clientId) != null ? true : false;
    }

    public void removeChannelContext(ChannelHandlerContext ctx) {
        String clientId = ctx.attr(NettyServerHandler.CHANNEL_KEY_CLIENT_ID).get();
        if (clientId == null) {
            return;
        }
        ctxs.remove(clientId, ctx);
    }

    public Channel getChannelById(String clientId) {
        ChannelHandlerContext ctx = ctxs.get(clientId);
        return ctx == null ? null : ctx.channel();
    }

    public int getSize() {
        return ctxs.size();
    }
}
