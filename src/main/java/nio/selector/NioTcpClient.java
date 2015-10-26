package nio.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO客户端 
 *  
 * @author shirdrn 
 */  
public class NioTcpClient {
    private final Logger log = LoggerFactory.getLogger(NioTcpClient.class.getName());
    private InetSocketAddress inetSocketAddress;  
      
    public NioTcpClient(String hostname, int port) {  
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }  
      
    /** 
     * 发送请求数据 
     * @param requestData 
     */  
    public void send(String requestData) {  
        try {  
            SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);  
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            socketChannel.write(ByteBuffer.wrap(requestData.getBytes()));  
            while (true) {  
                byteBuffer.clear();  
                int readBytes = socketChannel.read(byteBuffer);  
                if (readBytes > 0) {  
                    byteBuffer.flip();  
                    log.info("Client: readBytes = " + readBytes);  
                    log.info("Client: data = " + new String(byteBuffer.array(), 0, readBytes));  
                    socketChannel.close();  
                    break;  
                }  
            }  
  
        } catch (IOException e) {
            e.printStackTrace();  
        }  
    }  
      
    public static void main(String[] args) {
        String requestData = "Actions speak louder than words!";
        new NioTcpClient("localhost", 9999).send(requestData);
    }  
}  