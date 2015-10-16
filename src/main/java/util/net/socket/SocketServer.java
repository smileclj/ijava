package util.net.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lao on 2015/9/25.
 */
public class SocketServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private Map<String,Socket> clients = new ConcurrentHashMap<String,Socket>();

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
                        logger.info("服务端接收：" +br.readLine());
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

    public void startServer(int port){
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            //每有一个客户端连接，就生成一个socket，每一个socket有一个写线程与读线程
            while(true){
                Socket s = server.accept(); //阻塞
                clients.put(UUID.randomUUID().toString(),s);
                new ReadHandler(s).start();
                new WriteHandler(s).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.startServer(8000);
    }
}
