package com.sxt.chat04;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author lyl3878
 * @date 7/17/2019
 */
public class Receive implements Runnable {

    private DataInputStream dis;
    private Socket client;
    private boolean isRunning;

    public Receive(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());
            isRunning =true;
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = receive();
            if (!msg.equals("")) {
                System.out.println(msg);
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

    //释放资源
    private void release() {
        this.isRunning = false;
        //释放资源
        Utils.close(dis, client);
    }
}
