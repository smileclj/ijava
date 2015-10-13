package util.net.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by lao on 2015/9/25.
 */
public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private class ReadHandler extends Thread{
        private Socket s;

        public ReadHandler(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                while(true){
                    while(isr.ready()){
                        logger.info("客户端接收：" +br.readLine());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class WriteHandler extends Thread{
        private Socket s;

        public WriteHandler(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            PrintWriter pw = null;
            BufferedReader br = null;
            try {
                pw = new PrintWriter(s.getOutputStream(),true);
                while(true){
                    System.out.println(s.hashCode()+"请输入：");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    pw.println(br.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startClient(String host,int port){
        try {
            Socket s = new Socket(host,port);
            System.out.println("客户端启动");
            new ReadHandler(s).start();
            new WriteHandler(s).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        SocketClient client = new SocketClient();
//        client.startClient("127.0.0.1",8000);
        System.out.println(UUID.randomUUID().toString());
    }
}
