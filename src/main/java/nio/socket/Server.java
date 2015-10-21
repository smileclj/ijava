package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wana on 2015/10/20.
 */
public class Server {
    public static void main(String[] args) {
        SocketChannel channel = null;
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(9999));
            while(true){
                channel = server.accept();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                byte[] bs = new byte[1024];
                if(channel!=null){
                    channel.read(buffer);
                    buffer.get(bs);
                    System.out.println(new String(bs,"utf-8"));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
