package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by wana on 2015/10/21.
 */
public class Client {
    public static void main(String[] args) {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("127.0.0.1",9999));
            Scanner sc = new Scanner(System.in);
            while(sc.hasNext()){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
