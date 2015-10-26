package nio.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wana on 2015/10/26.
 */
public class NioTcpServer extends Thread {
    private final Logger logger = LoggerFactory.getLogger(NioTcpServer.class);
    private InetSocketAddress inetSocketAddress;
    private Handler handler = new ServerHandler();

    public NioTcpServer(String hostname, int port) {
        inetSocketAddress = new InetSocketAddress(hostname,port);
    }

    @Override
    public void run() {
        try {
            Selector selector = Selector.open();  //打开选择器
            ServerSocketChannel channel = ServerSocketChannel.open();  //打开通道
            channel.configureBlocking(false);  //非阻塞
            channel.bind(inetSocketAddress);
            channel.register(selector, SelectionKey.OP_ACCEPT);  // 向通道注册选择器和对应事件标识
            logger.info("server started");
            while(true){
                int num = selector.select();  //阻塞
                if(num > 0){ //如果有感兴趣的事件的至少一个通道
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioTcpServer("localhost",9999).start();
    }
}
