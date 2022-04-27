package com.learning.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName SocketServer
 * @Description TODO
 * @Author hufei
 * @Date 2022/4/8 14:42
 * @Version 1.0
 */
public class SocketServer {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("启动服务器....");
            Socket s = ss.accept();
            System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            System.out.println("客户端：" + mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write(mess + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
