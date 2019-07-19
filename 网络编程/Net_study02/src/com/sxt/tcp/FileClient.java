package com.sxt.tcp;

import java.io.*;
import java.net.Socket;

/**
 * 上传文件
 * 1、建立连接：使用Socket创建客户端+服务的地址和端口
 * 2、操作：输入输出流操作
 * 3、释放资源
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class FileClient {
    public static void main(String[] args) throws Exception {
        //1、建立连接：使用Socket创建客户端+服务的地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2、操作：输入输出流操作
        InputStream is = new BufferedInputStream(new FileInputStream("D:/Projects/java_learning/Net_study02/src/tou.jpg"));
        OutputStream os = new BufferedOutputStream(client.getOutputStream());
        byte[] flush = new byte[1024];
        int len = -1;
        while ((len = is.read(flush)) != -1) {
            os.write(flush, 0, len);
        }
        os.flush();

        // 3、释放资源
        os.close();
        is.close();
        client.close();
    }
}
