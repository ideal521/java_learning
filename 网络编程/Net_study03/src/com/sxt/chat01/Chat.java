package com.sxt.chat01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室:服务器
 * 目标：实现一个客户可以正常收发消息
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class Chat {
    public static void main(String[] args) {
        System.out.println("---------Server-----------");
        try {
            //1、指定端口 使用ServerSocket创建服务器
            ServerSocket server = new ServerSocket(8888);
            //2、阻塞式等待连接 accept
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接！");

            //3、操作：输入输出流操作
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String msg = dis.readUTF();

            //4、返回消息
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(msg);
            dos.flush();

            //释放资源
            dos.close();
            dis.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
