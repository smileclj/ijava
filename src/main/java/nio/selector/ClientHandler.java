package nio.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wana on 2015/10/26.
 */
public class ClientHandler implements Handler {
    private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void handleAccept(SelectionKey key) throws IOException {

    }

    @Override
    public void handleConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        channel.finishConnect();
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        SocketChannel socketChannel = (SocketChannel)key.channel();
        while(true) {
            int readBytes = socketChannel.read(byteBuffer);
            if(readBytes > 0) {
                logger.info("Client: readBytes = " + readBytes);
                logger.info("Client: data = " + new String(byteBuffer.array(), 0, readBytes));
                byteBuffer.flip();
                break;
            }
        }
        socketChannel.close();
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {

    }


}
