package nettyx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Tools {

    private static Logger logger = LoggerFactory.getLogger(Tools.class);

    /**
     * 将16进制字符串(32位)转为字节数组
     *
     * @param arg 16进制字符串,长度必须为偶数位
     * @return
     */
    public static byte[] hexString2bytes(String arg) {
        if (arg == null) {
            return new byte[0];
        }
        byte[] obj = new byte[arg.length() / 2];
        for (int i = 0; i < obj.length; i++) {
            obj[i] = (Integer.valueOf(arg.substring(i * 2, i * 2 + 2), 16)).byteValue();
        }

        return obj;
    }

    /**
     * 转化字符串为十六进制编码,16字节的数组会转化为32位字符串
     *
     * @param obj
     * @return
     */
    public static String toHexString(byte[] obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < obj.length; i++) {
            int ch = obj[i];
            if (ch < 0) {
                ch = ch + 256;
            }
            String str = Integer.toString(ch, 16);
            if (str.length() == 1) {
                sb.append("0");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static String createUUID(){
        return  UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        String str = Tools.createUUID();
        System.out.println(str);
        byte[] bs = Tools.hexString2bytes(str);
        System.out.println(Tools.toHexString(bs));
    }
}
