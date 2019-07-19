package com.sxt.tcp;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 创建客户端
 * 1、建立连接：使用Socket创建客户端+服务的地址和端口
 * 2、操作：输入输出流操作
 * 3、释放资源
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //1、建立连接：使用Socket创建客户端+服务的地址和端口
        Socket client = new Socket("localhost",8888);
        // 2、操作：输入输出流操作
        DataOutputStream dos= new DataOutputStream(client.getOutputStream());
        String data = "hello";
        dos.writeUTF(data);
        dos.flush();
        // 3、释放资源
        client.close();
    }
}
