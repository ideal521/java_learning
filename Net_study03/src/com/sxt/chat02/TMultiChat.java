package com.sxt.chat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室:服务器
 * 目标：使用多线程实现多个客户可以正常收发多条消息
 * 问题：
 * 1. 代码不好维护
 * 2. 客户端读写没有分开 必须先写后读
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class TMultiChat {
    public static void main(String[] args) {
        System.out.println("---------Server-----------");
        try {
            //1、指定端口 使用ServerSocket创建服务器
            ServerSocket server = new ServerSocket(8888);
            while (true) {
                //2、阻塞式等待连接 accept
                Socket client = server.accept();
                System.out.println("一个客户端建立了连接！");

                new Thread(() -> {

                    DataInputStream dis = null;
                    DataOutputStream dos = null;
                    try {
                        dis = new DataInputStream(client.getInputStream());
                        dos = new DataOutputStream(client.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    boolean isRunning = true;
                        while (isRunning) {
                            try {
                                //3、操作：输入输出流操作
                                String msg = dis.readUTF();

                                //4、返回消息
                                dos.writeUTF(msg);
                                dos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                                isRunning=false;
                            }
                        }
                        //释放资源
                    try {
                        if(null!=dos){
                            dos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(null!=dis) {
                            dis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(null!=client) {
                            client.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
