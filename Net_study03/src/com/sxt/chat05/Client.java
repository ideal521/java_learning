package com.sxt.chat05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 在线聊天室：客户端
 * 目标：使用多线程实现多个客户可以正常收发多条消息
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("-----------Client------------");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入用户名:");
            String name = br.readLine();

            //1、建立连接：使用Socket创建客户端+服务的地址和端口
            Socket client = new Socket("localhost", 8888);
            //2、客户端发送消息
           new Thread(new Send(client,name)).start();
           new Thread(new Receive(client)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
