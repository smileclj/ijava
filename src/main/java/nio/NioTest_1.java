package nio;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wana on 2015/10/20.
 */
public class NioTest_1 {
    public static void printBuffer(Buffer buffer) {
        System.out.println("limit:" + buffer.limit());
        System.out.println("capacity:" + buffer.capacity());
        System.out.println("position:" + buffer.position());
    }

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileChannel inChannel = null;
        FileOutputStream fos = null;
        FileChannel outChannel = null;
        try {
            URI uri = NioTest_1.class.getResource("/test.txt").toURI();
            File file = new File(uri);
            fis = new FileInputStream(file);
            fos = new FileOutputStream("D:\\test.txt");
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(500);
            int b = 0;
//            inChannel.read(buffer);
//            printBuffer(buffer);
            while ((b = inChannel.read(buffer)) != -1) {
                buffer.flip(); //读写转换   limit置为当前位置,position置为0
                outChannel.write(buffer);  //将0到limit的数据写入到通道
//                printBuffer(buffer);
                //执行这句前,p=limit，如果不执行clear江p置为0，当再执行read时即为读取0个字节，while循环会一直执行
                buffer.clear();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
