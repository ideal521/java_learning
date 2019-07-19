package com.sxt.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * 文件存储：接收端
 * 1、使用DatagramSocket 指定端口 创建接收端
 * 2、准备容器 封装成DatagramPacket包裹
 * 3、阻塞式接收包裹receive(DatagramPacket p)
 * 4、分析数据
 * <p>
 * 5、释放资源
 *
 * @author lyl3878
 * @date 7/16/2019
 */
public class UdpFileServer {
    public static void main(String[] args) throws Exception {
        System.out.println("接收方启动中。。。");
        //1、使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);
        // 2、准备容器 封装成DatagramPacket包裹\
        byte[] container = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(container, 0, container.length);
        // 3、阻塞式接收包裹receive(DatagramPacket p)
        server.receive(packet);
        // 4、分析数据
        byte[] datas = packet.getData();
        int len = packet.getLength();

        IOUtils.byteArrayToFile(datas, "D:/Projects/java_learning/Net_study02/src/copy.png");


        // 5、释放资源
        server.close();
    }
}
