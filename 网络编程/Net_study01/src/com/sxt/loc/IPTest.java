package com.sxt.loc;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lyl3878
 * @date 7/16/2019
 */
public class IPTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getHostName());

        InetAddress[] address = InetAddress.getAllByName("www.baidu.com");

    }
}
