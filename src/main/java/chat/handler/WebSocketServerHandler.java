package chat.handler;

import chat.util.QueueUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

/**
 * Created by wana on 2015/11/25.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息");
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        try {
            // 如果HTTP解码失败，返回HTTP异常
            if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                return;
            }

            // 构造握手响应返回，本机测试
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8888", null, false);
            handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
                String str = req.headers().getAndConvert("Sec-WebSocket-Key");
                //握手成功才建立存储
                QueueUtil.getInstance().addSocket(ctx, str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

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
            // 本例程仅支持文本消息，不支持二进制消息
            if (!(frame instanceof TextWebSocketFrame)) {
                throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
            }
            // 返回应答消息
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println("服务端接收到的消息===" + request);
            QueueUtil.getInstance().broadMsg(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
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
            e.printStackTrace();
        }
    }

    //客户端断开连接会调用，需要队列中移除通道连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        QueueUtil.getInstance().removeSocket(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        QueueUtil.getInstance().removeSocket(ctx);
        ctx.close();
    }
}
