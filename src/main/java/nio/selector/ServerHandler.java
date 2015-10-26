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
public class ServerHandler implements Handler {
    private final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();  //与客户端一一对应的通道
        logger.info("Server: accept client socket " + socketChannel);
        socketChannel.configureBlocking(false);  //非阻塞
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    @Override
    public void handleConnect(SelectionKey key) throws IOException {

    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        SocketChannel socketChannel = (SocketChannel)key.channel();
        while(true) {
            int readBytes = socketChannel.read(byteBuffer);
            if(readBytes > 0) {
                logger.info("Server: readBytes = " + readBytes);
                logger.info("Server: data = " + new String(byteBuffer.array(), 0, readBytes));
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                break;
            }
        }
        socketChannel.close();
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.flip();
        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.write(byteBuffer);
        if(byteBuffer.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        byteBuffer.compact();
    }


}
