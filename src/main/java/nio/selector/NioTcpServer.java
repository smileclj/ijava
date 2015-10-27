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
 * Created by clj on 2015/10/26.
 */
public class NioTcpServer extends Thread {
    private final Logger logger = LoggerFactory.getLogger(NioTcpServer.class);
    private InetSocketAddress inetSocketAddress;
    private ServerHandler handler;
    private Selector selector;
    private ServerSocketChannel serverChannel;

    public NioTcpServer(int port,ServerHandler handler) {
        this.inetSocketAddress = new InetSocketAddress("127.0.0.1",port);
        this.handler = handler;
        try {
            selector = Selector.open();  //打开选择器
            serverChannel = ServerSocketChannel.open();  //打开通道
            serverChannel.socket().bind(inetSocketAddress);
            serverChannel.configureBlocking(false);  //非阻塞
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);  // 向通道注册选择器和对应事件标识
            logger.info("server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new NioTcpServer(9999,new ServerHandler()).start();
    }
}
