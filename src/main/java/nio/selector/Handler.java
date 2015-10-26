package nio.selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by wana on 2015/10/26.
 */
public interface Handler {
    /**
     * 处理{@link SelectionKey#OP_ACCEPT}事件
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;
    /**
     * 处理{@link SelectionKey#OP_CONNECT}事件
     * @param key
     * @throws IOException
     */
    void handleConnect(SelectionKey key) throws IOException;
    /**
     * 处理{@link SelectionKey#OP_READ}事件
     * @param key
     * @throws IOException
     */
    void handleRead(SelectionKey key) throws IOException;
    /**
     * 处理{@link SelectionKey#OP_WRITE}事件
     * @param key
     * @throws IOException
     */
    void handleWrite(SelectionKey key) throws IOException;
}
