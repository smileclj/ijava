//package nettyx.message;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.UnpooledByteBufAllocator;
//import nettyx.util.Tools;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 自定义消息请继承此类
// *
// */
//public abstract class AbstractMessage implements Serializable {
//    private static final long serialVersionUID = -3290084935119988398L;
//
//    private static final Logger log = LoggerFactory.getLogger(AbstractMessage.class);
//    //总共34个字节
//    private int length; // 报文长度；4 byte(包括报头的34个字节)
//    private short version; // 版本；2 byte
//    private int command; // 命令代码；4 byte
//    private String messageId; // 报文唯一编号,实际上是32byte，传输时转为 16 byte
//    private Date creationTime = new Date();// 创建时间8byte
//
//    public AbstractMessage() {
//    }
//
//    /**
//     * 编码成字节数组
//     *
//     * @return
//     */
//    public ByteBuf encode() {
//        byte[] body = getBodyBytes();
//        length = 34 + (body == null ? 0 : body.length);
//        ByteBuf bb = UnpooledByteBufAllocator.DEFAULT.buffer(length);
//        bb.writeInt(length);
//        bb.writeShort(version);
//        bb.writeInt(getCommand());
//        bb.writeBytes(Tools.hexString2bytes(messageId));
//        bb.writeLong(creationTime.getTime());
//        if (body != null) {
//            bb.writeBytes(body);
//        }
//        return bb;
//    }
//
//    /**
//     * 从字节数组解码成对象
//     */
//    public void decode(ByteBuf bb) {
//        length = bb.readInt();
//        version = bb.readShort();
//        command = bb.readInt();
//        byte[] seq = new byte[16];
//        bb.readBytes(seq); // get sequence
//        messageId = Tools.toHexString(seq);
//        creationTime = new Date(bb.readLong());
//
//        valueOf(bb);
//    }
//
//    /**
//     * 通过iobuffer得到消息实例
//     *
//     * @param buffer
//     * @return
//     */
//    public static AbstractMessage bytesToObject(ByteBuf buffer) {
//        AbstractMessage m = null;
//        int remain = buffer.readableBytes();
//        if (remain < 4) {
//            return null;
//        }
//        int length = buffer.readInt();
//        if (remain < length) {
//            return null;
//        }
//        short version = buffer.readShort();
//        int command = buffer.readInt();
//        Class<? extends AbstractMessage> clazz = messageClassesMap.get(command);
//        if (clazz == null) {
//            throw new IllegalArgumentException("command{" + Integer.toHexString(command)
//                    + "} is not registered, please call registerMessageClass() to register messageClass.");
//        }
//        byte[] b = new byte[16];
//        buffer.get(b);
//        String messageId = Tools.toHexString(b);
//        Date creationTime = new Date(buffer.getLong());
//        try {
//            m = clazz.newInstance();
//            m.valueOf(buffer);
//            m.length = length;
//            m.version = version;
//            m.command = command;
//            m.messageId = messageId;
//            m.creationTime = creationTime;
//        }
//        catch (Exception e) {
//            log.error("新建消息实例异常：{}", e);
//            return null;
//        }
//        return m;
//    }
//
//    /**
//     * 把字节数组转换成消息
//     *
//     * @param body
//     * @return
//     */
//    public static AbstractMessage fromBytes(IoBuffer bodyBuffer) {
//        try {
//            int command = bodyBuffer.getInt(bodyBuffer.position() + 6);
//            Class<? extends AbstractMessage> m = messageClassesMap.get(command);
//            if (m == null) {
//                throw new IllegalArgumentException("command{" + Integer.toHexString(command)
//                        + "} is not registered, please call registerMessageClass() to register messageClass.");
//            }
//            AbstractMessage ret = m.newInstance();
//            ret.decode(bodyBuffer);
//
//            return ret;
//        }
//        catch (Exception e) {
//            log.error("", e);
//        }
//
//        return null;
//    }
//
//    /**
//     * 把JSONObject转换成消息
//     *
//     * @param body
//     * @return
//     */
//    public static AbstractMessage fromJSONObject(JSONObject obj) {
//        if (obj == null) {
//            return null;
//        }
//        try {
//            int command = obj.getInt("command");
//            obj.remove("command");
//            Class<? extends AbstractMessage> m = messageClassesMap.get(command);
//            if (m == null) {
//                throw new IllegalArgumentException("command{" + Integer.toHexString(command)
//                        + "} is not registered, please call registerMessageClass() to register messageClass.");
//            }
//            AbstractMessage ret = (AbstractMessage) JSONObject.toBean(obj, m);
//
//            return ret;
//        }
//        catch (Exception e) {
//            log.error("", e);
//        }
//
//        return null;
//    }
//
//    private static final Map<Integer, Class<? extends AbstractMessage>> messageClassesMap = new HashMap<Integer, Class<? extends AbstractMessage>>();
//    static {
//        // tocc messages
//        messageClassesMap.put(ActiveMessage.COMMAND, ActiveMessage.class);
//        messageClassesMap.put(ActiveRespMessage.COMMAND, ActiveRespMessage.class);
//
//        messageClassesMap.put(LoginMessage.COMMAND, LoginMessage.class);
//        messageClassesMap.put(LoginRespMessage.COMMAND, LoginRespMessage.class);
//
//        messageClassesMap.put(QuitMessage.COMMAND, QuitMessage.class);
//        messageClassesMap.put(QuitRespMessage.COMMAND, QuitRespMessage.class);
//
//        messageClassesMap.put(SplitedMessage.COMMAND, SplitedMessage.class);
//    }
//
//    /**
//     * 子类实现，返回命令代码
//     *
//     * @return
//     */
//    public abstract int getCommand();
//
//    /**
//     * 得到命令的字符串形式，比如0x00000001
//     *
//     * @return
//     */
//    public String getCommandStr() {
//        return Integer.toHexString(getCommand());
//    }
//
//    /**
//     * 子类实现，返回报文体的字节数组
//     *
//     * @return
//     */
//    public abstract byte[] getBodyBytes();
//
//    /**
//     * 子类实现，对报文体部分进行解码
//     *
//     */
//    public abstract void valueOf(ByteBuf buf);
//
//    public void setMessageId(String messageId) {
//        this.messageId = messageId;
//    }
//
//    /**
//     * 取得messageid,32位长度
//     *
//     * @return
//     */
//    public String getMessageId() {
//        return messageId;
//    }
//
//    /**
//     * 得到报文长度（包括报头）,此方法不建议框架外部调用，调用此方法前注意，如果调用此方法后改变了消息属性，则必须调用resetLength重置长度。
//     *
//     * @return
//     */
//    public int getLength() {
//        if (length == 0) {
//            encode();
//        }
//        return length;
//    }
//}
