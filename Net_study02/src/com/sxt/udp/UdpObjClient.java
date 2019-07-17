package com.sxt.udp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * 引用类型：发送端
 * 1、使用DatagramSocket 指定端口 创建发送端
 * 2、将基本类型要转成字节数组
 * 3、封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹send(DatagramPacket p)
 * <p>
 * 5、释放资源
 *
 * @author lyl3878
 * @date 7/16/2019
 */
public class UdpObjClient {
    public static void main(String[] args) throws Exception {
        System.out.println("发送方启动中。。。");
        //1、使用DatagramSocket 指定端口 创建发送端
        DatagramSocket client = new DatagramSocket(8888);
        // 2、准备数据一定要转成字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        //操作基本类型+数据
        oos.writeUTF("编码辛酸泪");
        oos.writeInt(18);
        oos.writeBoolean(false);
        oos.writeChar('a');
        //对象
        oos.writeObject("吼吼");
        oos.writeObject(new Date());
        Employee emp = new Employee("马云",400);
        oos.writeObject(emp);
        oos.flush();

        byte[] datas = baos.toByteArray();

        // 3、封装成DatagramPacket包裹，需要指定目的地
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length,
                new InetSocketAddress("localhost", 9999));
        // 4、发送包裹send(DatagramPacket p)
        client.send(packet);
        // 5、释放资源
        client.close();
    }
}
