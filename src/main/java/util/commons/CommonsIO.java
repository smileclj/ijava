package util.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by lao on 2015/9/25.
 */
public class CommonsIO {
    private static final Logger logger = LoggerFactory.getLogger(CommonsIO.class);

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String f = CommonsIO.class.getResource("/test.txt").getFile();
//            CommonsIO.class.getClassLoader().getResource("/test.txt");
            File file = new File(f);
//            String s = FileUtils.readFileToString(file);
//            logger.info(s);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            fos = new FileOutputStream("D:\\test.txt");
            bos = new BufferedOutputStream(fos);
            while(bis.available() >0){
                bos.write(bis.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
