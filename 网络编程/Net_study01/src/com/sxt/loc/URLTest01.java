package com.sxt.loc;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL:统一资源定位器，互联网三在基三石之一（html http）,区分资源
 * 1、 协议
 * 2、 域名、计算机
 * 3、 端口：默认80
 * 4、 请求资源
 *
 * @author lyl3878
 * @date 7/16/2019
 */
public class URLTest01 {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://www.baidu.com:80");

        //获取四个值
        System.out.println("协议：" + url.getProtocol());
        System.out.println("域名|IP:" + url.getHost());
        System.out.println("端口：" + url.getPort());
        System.out.println("请求资源1：" + url.getFile());
        System.out.println("请求资源2：" + url.getPath());

        //获取参数
        System.out.println("参数：" + url.getQuery());
        //获取锚点
        System.out.println("锚点：" + url.getRef());
    }
}
