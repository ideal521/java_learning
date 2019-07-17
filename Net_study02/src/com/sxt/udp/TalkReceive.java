package com.sxt.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 接收端：使用面向对象封装
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class TalkReceive implements Runnable {

    private DatagramSocket server;
    private String from;

    public TalkReceive(int port,String from) {
        this.from=from;

        try {
            server = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 2、准备容器 封装成DatagramPacket包裹\
                byte[] container = new byte[1024 * 60];
                DatagramPacket packet = new DatagramPacket(container, 0, container.length);
                // 3、阻塞式接收包裹receive(DatagramPacket p)
                server.receive(packet);
                // 4、分析数据
                byte[] datas = packet.getData();
                int len = packet.getLength();
                String data = new String(datas, 0, len);
                System.out.println(from+":"+data);
                if (data.equals("bye")) {
                    break;
                }
            }
            // 5、释放资源
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
