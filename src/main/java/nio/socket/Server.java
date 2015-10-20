package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
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
                if(channel!=null){

//                    channel.read()
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
