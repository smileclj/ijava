package io.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient
{  
    // 定义发送数据报的目的地  
    public static final int DEST_PORT = 30000;  
    public static final String DEST_IP = "127.0.0.1";  
    // 定义每个数据报的最大大小为4KB  
    private static final int DATA_LEN = 4096;     
    // 定义接收网络数据的字节数组  
    byte[] inBuff = new byte[DATA_LEN];  
    // 以指定的字节数组创建准备接收数据的DatagramPacket对象  
    private DatagramPacket inPacket =
        new DatagramPacket(inBuff , inBuff.length);  
    // 定义一个用于发送的DatagramPacket对象  
    private DatagramPacket outPacket = null;  
    public void init()throws IOException
    {  
        try(  
            // 创建一个客户端DatagramSocket，使用随机端口  
            DatagramSocket socket = new DatagramSocket())
        {  
            // 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组  
            outPacket = new DatagramPacket(new byte[0] , 0  
                , InetAddress.getByName(DEST_IP) , DEST_PORT);
            // 创建键盘输入流  
            Scanner scan = new Scanner(System.in);
            // 不断地读取键盘输入  
            while(scan.hasNextLine())  
            {  
                // 将键盘输入的一行字符串转换成字节数组  
                byte[] buff = scan.nextLine().getBytes();  
                // 设置发送用的DatagramPacket中的字节数据  
                outPacket.setData(buff);  
                // 发送数据报  
                socket.send(outPacket);  
                // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中  
                socket.receive(inPacket);  
                System.out.println(new String(inBuff , 0  
                    , inPacket.getLength()));  
            }  
        }  
    }  
    public static void main(String[] args)   
        throws IOException  
    {  
        new UdpClient().init();  
    }  
}  