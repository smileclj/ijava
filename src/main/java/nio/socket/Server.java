package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wana on 2015/10/20.
 */
public class Server {
    private class ReadHandler extends Thread{
        private SocketChannel channel;

        public ReadHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            try {
                while(true){   //限制每次输入的字符数
                    int b = channel.read(buffer);
                    if(b == -1){
                        break;
                    }
                    buffer.flip();
                    byte[] bs = new byte[buffer.remaining()];
                    buffer.get(bs);
                    buffer.clear();
                    System.out.println(new String(bs));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private class WriteHandler extends Thread{
        private SocketChannel channel;

        public WriteHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            super.run();
        }
    }

    public void startServer(int port){
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(port));
            while(true){
                SocketChannel channel = server.accept();
                new ReadHandler(channel).start();
                new WriteHandler(channel).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(9999);
    }
}
