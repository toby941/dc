package com.dc.web.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketClient {

    private static final Logger log = Logger.getLogger(SocketClient.class);

    public static Integer port;
    // 定义每个数据报的最大大小为4K
    private static final int DATA_LEN = 4096;
    // 定义该服务器使用的DatagramSocket
    private final DatagramSocket socket = null;
    // 定义接收网络数据的字节数组
    static byte[] inBuff = new byte[DATA_LEN];
    // 以指定字节数组创建准备接受数据的DatagramPacket对象
    private static final DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
    // 定义一个用于发送的DatagramPacket对象
    private DatagramPacket outPacket;

    public static Boolean noticeTCP() throws IOException {
        Socket server = new Socket(InetAddress.getLocalHost(), port); // 向主机名为InetAddress.getLocalHost()的服务器申请连接
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream())); // 客户端建立输入流并进行封装
        PrintWriter out = new PrintWriter(server.getOutputStream());

        String str = "done";

        out.println(str); // 客户端向服务器发送信息
        out.flush();
        log.error("send notice done port: " + port);
        String result = in.readLine();
        server.close();
        log.error("receive data : " + result);
        if ("done".equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean notice() throws IOException {
        try {
            // 创建一个客户端DatagramSocket，使用随机端口
            DatagramSocket socket = new DatagramSocket();
            // socket.setSoTimeout(10 * 1000);
            // 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组
            DatagramPacket outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), port);
            byte[] buff = "done".getBytes();
            // 设置发送用的DatagramPacket里的字节数据
            outPacket.setData(buff);
            // 发送数据报
            log.error("send notice done port: " + port);
            socket.send(outPacket);
            // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组里。
            socket.receive(inPacket);
            String result = new String(inBuff, 0, inPacket.getLength());
            log.error("receive data : " + result);
            if ("done".equals(result)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("socket port:" + port, e);
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String ip = "http://zhaduir.vicp.cc";
        // String ip=InetAddress.getLocalHost();
        Socket server = new Socket(ip, 5912); // 向主机名为InetAddress.getLocalHost()的服务器申请连接
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream())); // 客户端建立输入流并进行封装
        PrintWriter out = new PrintWriter(server.getOutputStream());
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in)); // 客户端从键盘输入信息

        while (true) {
            String str = wt.readLine(); // 客户端读取（获得）键盘的字符串

            // String str1 = in.readLine(); // 从服务器获得字符串
            out.println(str); // 客户端向服务器发送信息
            out.flush();
            if (str.equals("end")) {
                break;
            }
            System.out.println(in.readLine());
        }
        server.close();
    }
}
