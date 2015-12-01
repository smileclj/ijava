package nettyx.handler;

import io.netty.util.AttributeKey;
import nettyx.entity.CommonUser;

/**
 * Created by wana on 2015/11/30.
 */
public class NettyServerHandler {
    public static final AttributeKey<String> CHANNEL_KEY_CLIENT_ID = AttributeKey.valueOf("clientId");
    public static final AttributeKey<CommonUser> CHANNEL_KEY_CLIENT_INFO = AttributeKey.valueOf("clientInfo");
}
