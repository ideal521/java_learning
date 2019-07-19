package com.sxt.chat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室:服务器
 * 目标：实现多个客户可以正常收发多条消息
 * 问题：其他客户必须等待这前的客户退出，才能继续排队
 * @author lyl3878
 * @date 7/17/2019
 */
public class MultiChat {
    public static void main(String[] args) {
        System.out.println("---------Server-----------");
        try {
            //1、指定端口 使用ServerSocket创建服务器
            ServerSocket server = new ServerSocket(8888);
            while (true) {
                //2、阻塞式等待连接 accept
                Socket client = server.accept();
                System.out.println("一个客户端建立了连接！");

                DataInputStream dis = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                boolean isRunning = true;
                while (true) {
                    //3、操作：输入输出流操作
                    String msg = dis.readUTF();

                    //4、返回消息
                    dos.writeUTF(msg);
                    dos.flush();
                }
            }

            //释放资源
//            dos.close();
//            dis.close();
//            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
