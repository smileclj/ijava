package nettyx.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import nettyx.util.ChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wana on 2015/11/30.
 */
public class NettyWsServerHandler extends SimpleChannelInboundHandler<Object> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebSocketServerHandshaker handshaker;
    private String serverName;
    private int serverPort;
    private ChannelManager channelManager;

    public NettyWsServerHandler(String serverName, int serverPort, ChannelManager channelManager) {
        this.serverPort = serverPort;
        this.serverName = serverName;
        this.channelManager = channelManager;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 传统的HTTP接入,websocket建立握手是通过发送http请求的
        if (msg instanceof FullHttpRequest) {
            logger.info("receive handshaker http message!!!");
            _handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            logger.info("receive websocket socket message!!!");
            _handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 处理http消息
     *
     * @param ctx
     * @param req
     */
    private void _handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        try {
            // 如果HTTP解码失败，返回HTTP异常
            if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
                _sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                return;
            }

            // 构造握手响应返回
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://127.0.0.1:" + String.valueOf(serverPort), null, false);
            handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
                logger.info("shake hands success!!");
//                String str = req.headers().getAndConvert("Sec-WebSocket-Key");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            // 判断是否是关闭链路的指令
            if (frame instanceof CloseWebSocketFrame) {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
                return;
            }
            // 判断是否是Ping消息
            if (frame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
                return;
            }
            // 只支持文本消息，不支持二进制消息
            if (!(frame instanceof TextWebSocketFrame)) {
                throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
            }
            // 返回应答消息
            String request = ((TextWebSocketFrame) frame).text();
            logger.info("服务端接收到消息===" + request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * http响应
     *
     * @param ctx
     * @param req
     * @param res
     */
    private void _sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        try {
            // 返回应答给客户端
            if (res.status().code() != 200) {
                ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
                res.content().writeBytes(buf);
                buf.release();
                res.headers().set("content-length", String.valueOf(res.content().readableBytes()));
            }

            // 如果是非Keep-Alive，关闭连接
            ChannelFuture f = ctx.channel().writeAndFlush(res);

            if (!Boolean.valueOf(res.headers().get("keep-alive").toString()) || res.status().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    //客户端断开连接会调用，需要队列中移除通道连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(),cause);
        ctx.close();
    }
}
