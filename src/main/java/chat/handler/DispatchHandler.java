package chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by wana on 2015/11/26.
 */
public class DispatchHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("dispatcher=====");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        buf.readerIndex(0);
//        String body = new String(req);
//        System.out.println(body);
        System.out.println(_decode(req));

        super.channelRead(ctx, msg);
    }

    private String _decode(byte[] receivedDataBuffer) throws UnsupportedEncodingException {
        String result = null;
        //数据开始的位数  前面2个byte 固定必须存在
        int dataStartIndex = 2;
        //查看第一帧的值  代表是否结束
        int isend = receivedDataBuffer[0] >> 7 & 0x1;
        System.out.println("是否结束：【" + (isend == 1 ? "yes" : "no") + "】");
        //获取是否需要掩码
        boolean mask = ((receivedDataBuffer[1] >> 7 & 0x1) == 1) ? true : false;
        System.out.println("掩码：【" + (mask ? "yes" : "no") + "】");
        //Payload length: 传输数据的长度，以字节的形式表示：7位、7+16位、或者7+64位。
        //如果这个值以字节表示是0-125这个范围，那这个值就表示传输数据的长度；
        //如果这个值是126，则随后的两个字节表示的是一个16进制无符号数，用来表示传输数据的长度；
        //如果这个值是127,则随后的是8个字节表示的一个64位无符合数，这个数用来表示传输数据的长度
        int dataLength = receivedDataBuffer[1] & 0x7F;
        System.out.println("描述消息长度：【" + dataLength + "】");
        //查看 消息描述 是否大于 126 如果大于
        if (dataLength < 126) {
            //126以内取本身
        } else if (dataLength == 126) {
            dataStartIndex = dataStartIndex + 2;
        } else if (dataLength == 127) {
            dataStartIndex = dataStartIndex + 8;
        }

        //掩码数组
        byte[] frameMaskingAry = new byte[4];
        if (mask) {
            for (int i = 0; i < frameMaskingAry.length; i++) {
                frameMaskingAry[i] = receivedDataBuffer[dataStartIndex + i];
            }
            dataStartIndex += 4;
        }

        // 计算非空位置
        int lastStation = receivedDataBuffer.length - 1;

        // 利用掩码对org-data进行异或
        int frame_masking_key = 0;

        //保存数据的 数组
        byte[] dataByte = new byte[lastStation - dataStartIndex + 1];

        if (mask) {
            for (int i = dataStartIndex; i <= lastStation; i++) {
                //把数据进行异或运算
                receivedDataBuffer[i] = (byte) (receivedDataBuffer[i] ^ frameMaskingAry[frame_masking_key % 4]);
                //把进行异或运算之后的 数据放入数组
                dataByte[i - dataStartIndex] = receivedDataBuffer[i];
                frame_masking_key++;
            }
        }

        result = new String(dataByte, "UTF-8");
        return result;
    }

}
