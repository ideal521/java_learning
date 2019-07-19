package com.sxt.chat05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 在线聊天室:服务器
 * 目标：实现私聊
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class Chat {

    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("---------Server-----------");
        try {
            //1、指定端口 使用ServerSocket创建服务器
            ServerSocket server = new ServerSocket(8888);
            while (true) {
                //2、阻塞式等待连接 accept
                Socket client = server.accept();
                System.out.println("一个客户端建立了连接！");
                Channel c = new Channel(client);
                all.add(c);
                new Thread(c).start();
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
        private String name;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                this.name = receive();
                this.send("欢迎" + this.name + "来到聊天室！");
                this.sendOthers(this.name + "来到了聊天室！", true);
            } catch (IOException e) {
                release();
            }
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
                    //send(msg);
                    sendOthers(msg, false);
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

        /**
         * 群聊：获取自己的消息，发送给其他人
         * 私聊：约定数据格式：@xxxx:msg
         *
         * @param msg
         * @param isSystem
         */
        private void sendOthers(String msg, boolean isSystem) {

            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) { //私聊
                int idx = msg.indexOf(":");
                //获取目标和数据
                String targetName = msg.substring(1, idx);
                msg = msg.substring(idx + 1);

                for (Channel other : all) {
                    if (other.name.equals(targetName)) {
                        other.send(this.name + "悄悄对您说：" + msg);
                        break;
                    }
                }
            } else {

                for (Channel other : all) {
                    if (other == this) {
                        continue;
                    }
                    if (isSystem) {
                        other.send(msg);
                    } else {
                        other.send(this.name + "对所有人说：" + msg);
                    }
                }
            }
        }

        //释放资源
        private void release() {
            this.isRunning = false;
            //释放资源
            Utils.close(dos, dis, client);

            all.remove(this);
            sendOthers(this.name + "离开了聊天室！", true);
        }
    }
}
