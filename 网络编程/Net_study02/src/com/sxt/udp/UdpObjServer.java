package com.sxt.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * 引用类型：接收端
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
public class UdpObjServer {
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

        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(datas)));

        //顺序与写出一致
        String msg = ois.readUTF();
        int age = ois.readInt();
        boolean flag = ois.readBoolean();
        char ch = ois.readChar();
        //对象的数据还原
        Object str = ois.readObject();
        Object date = ois.readObject();
        Object employee = ois.readObject();

        if (str instanceof String) {
            String strObj = (String) str;
            System.out.println(strObj);
        }

        if (date instanceof Date) {
            Date dateObj = (Date) date;
            System.out.println(dateObj);
        }

        if (employee instanceof Employee) {
            Employee empObj = (Employee) employee;
            System.out.println(empObj.getName() + "-->" + empObj.getSalary());
        }

        System.out.println(msg + "-->" + flag);


        // 5、释放资源
        server.close();
    }
}
