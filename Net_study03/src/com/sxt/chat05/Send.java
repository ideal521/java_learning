package com.sxt.chat05;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 使用多线程封装：接收端
 * 1、发送消息
 * 2、释放资源
 * 3、重写run
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class Send implements Runnable {
    private BufferedReader console;
    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning = false;
    private String name;

    public Send(Socket client, String name) {
        this.client = client;
        this.name = name;

        console = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
            send(name);
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = getStrFromConsole();
            if (!msg.equals("")) {
                send(msg);
            }
        }
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
     * 从控制台获取消息
     *
     * @return
     */
    private String getStrFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //释放资源
    private void release() {
        this.isRunning = false;
        //释放资源
        Utils.close(dos, client);
    }
}
