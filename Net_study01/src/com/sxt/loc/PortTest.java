package com.sxt.loc;

import java.net.InetSocketAddress;

/**
 * @author lyl3878
 * @date 7/16/2019
 */
public class PortTest {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost",9000);
        System.out.println(socketAddress.getAddress());
    }
}
