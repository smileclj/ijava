package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by wana on 2015/10/21.
 */
public class Client {
    private class ReadHandler extends Thread{
        private SocketChannel channel;

        public ReadHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            super.run();
        }
    }

    private class WriteHandler extends Thread{
        private SocketChannel channel;

        public WriteHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            try {
                while(true){
                    System.out.println("请输入");
                    while(sc.hasNext()){
                        channel.write(ByteBuffer.wrap(sc.next().getBytes()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startClient(String host,int port){
        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(host,port));
            new ReadHandler(channel).start();
            new WriteHandler(channel).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient("127.0.0.1",9999);
    }
}
