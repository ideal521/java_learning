package com.sxt.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟登录-单向 创建服务器
 * 1、指定端口 使用ServerSocket创建服务器
 * 2、阻塞式等待连接 accept
 * 3、操作：输入输出流操作
 * 4、释放资源
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class LoginMultiServer {
    public static void main(String[] args) throws Exception {
        //1、指定端口 使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        boolean isRunning = true;

        while (isRunning) {
            //2、阻塞式等待连接 accept
            Socket client = server.accept();
            System.out.println("一个客服端建立了连接");
            new Thread(new Channel(client)).start();
        }
        server.close();
    }

    static class Channel implements Runnable {
        private Socket client;
        private DataInputStream dis; //输入流
        private DataOutputStream dos; //输出流

        public Channel(Socket client) {
            this.client = client;
            try {
                //输入
                dis = new DataInputStream(client.getInputStream());
                //输出
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();

                release();
            }
        }

        @Override
        public void run() {
            try {
                //3、操作：输入输出流操作

                String username = "";
                String pwd = "";
                //分析
                String[] dataArray = receive().split("&");
                for (String info : dataArray) {
                    String[] userInfo = info.split("=");
                    if (userInfo[0].equals("username")) {
                        System.out.println("你输入的用户名为：" + userInfo[1]);
                        username = userInfo[1];
                    } else if (userInfo[0].equals("pwd")) {
                        System.out.println("你输入的密码为：" + userInfo[1]);
                        pwd = userInfo[1];
                    }
                }
                //输出
                if (username.equals("ideal") && pwd.equals("123456")) {
                    send("登录成功，欢迎回来！");
                } else {
                    send("用户名或密码错误！");
                }

                //4、释放资源
                release();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 接收数据
         *
         * @return
         */
        private String receive() {
            String datas = null;
            try {
                datas = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return datas;
        }

        /**
         * 发送数据
         *
         * @param msg
         */
        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void release() {
            try {
                if(null!=dos){
                    dos.close();
                }
                if(null!=dis){
                    dis.close();
                }
                if(null!=client){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
