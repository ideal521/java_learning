package com.sxt.chat03;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室:服务器
 * 目标：使用多线程实现多个客户可以正常收发多条消息
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

                new Thread(new Channel(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //一个客户代表一个Channel
    static class Channel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        private boolean isRunning = false;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
            } catch (IOException e) {
                release();
            }
        }

        @Override
        public void run() {
            while (isRunning){
                String msg = receive();
                if(!msg.equals("")){
                    send(msg);
                }
            }
        }

        //接收消息
        private String receive() {
            String msg = "";
            try {
                //3、操作：输入输出流操作
                msg = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
            return msg;
        }

        //发送消息
        private void send(String msg) {
            //4、返回消息
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
        }

        //释放资源
        private void release() {
            this.isRunning=false;
            //释放资源
            Utils.close(dos, dis, client);
        }
    }
}
