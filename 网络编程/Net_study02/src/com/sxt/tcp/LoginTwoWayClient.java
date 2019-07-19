package com.sxt.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 模拟登录-双向-创建客户端
 * 1、建立连接：使用Socket创建客户端+服务的地址和端口
 * 2、操作：输入输出流操作
 * 3、释放资源
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class LoginTwoWayClient {
    public static void main(String[] args) throws Exception {
        System.out.println("---------Client----------");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入用户名：");
        String username = reader.readLine();
        System.out.println("请输入密码：");
        String pwd = reader.readLine();

        //1、建立连接：使用Socket创建客户端+服务的地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2、操作：输入输出流操作
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("username=" + username + "&pwd=" + pwd);
        dos.flush();

        DataInputStream dis = new DataInputStream(client.getInputStream());
        String result = dis.readUTF();
        System.out.println(result);
        // 3、释放资源
        client.close();
    }
}
