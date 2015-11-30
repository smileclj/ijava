package nettyx.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import nettyx.entity.CommonUser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wana on 2015/11/30.
 */
public class ChannelManager {
    //    private ConcurrentHashMap<String, ChannelHandlerContext> ctxs = new ConcurrentHashMap<String, ChannelHandlerContext>();
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public boolean isUserAlreadyLogin(ChannelId clientId) {
        return channels.find(clientId) != null ? true : false;
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public Channel getChannelById(ChannelId clientId) {
        return channels.find(clientId);
    }

    public int getSize() {
        return channels.size();
    }
}
