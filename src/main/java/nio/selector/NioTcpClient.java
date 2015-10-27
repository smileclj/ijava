package nio.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by clj on 2015/10/26.
 */
public class NioTcpClient extends Thread{
    private final Logger logger = LoggerFactory.getLogger(NioTcpClient.class.getName());
    private Selector selector;
    private SocketChannel clientChannel;
    private InetSocketAddress inetSocketAddress;
    private ClientHandler handler;
      
    public NioTcpClient(String hostname, int port,ClientHandler handler) {
        inetSocketAddress = new InetSocketAddress(hostname, port);
        this.handler = handler;
        try {
            selector = Selector.open();
            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            //可以设置TCP连接参数
//            clientChannel.socket().setReuseAddress(true);
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
            boolean connected = clientChannel.connect(inetSocketAddress);
            if(connected){
                clientChannel.register(selector,SelectionKey.OP_READ);
            }else{
                clientChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
      
    /** 
     * 发送请求数据 
     * @param message
     */  
    public void sendMessage(String message) {
        try {
            clientChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                int num = selector.select();
                if(num > 0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while(it.hasNext()){
                        SelectionKey key = it.next();
                        if(key.isAcceptable()){
                            logger.info("accept");
                            handler.handleAccept(key);
                        }else if(key.isConnectable()){
                            logger.info("connect");
                            handler.handleConnect(key);
                        }else if(key.isReadable()){
                            logger.info("read");
                            handler.handleRead(key);
                        }else if(key.isWritable()){
                            logger.info("write");
                            handler.handleWrite(key);
                        }
                        it.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String message = "Actions speak louder than words!";
        NioTcpClient client = new NioTcpClient("127.0.0.1", 9999,new ClientHandler());
        client.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.sendMessage(message);
    }  
}  