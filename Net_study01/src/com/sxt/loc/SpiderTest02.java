package com.sxt.loc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络爬虫的原理+模拟浏览器
 * @author lyl3878
 * @date 7/16/2019
 */
public class SpiderTest02 {
    public static void main(String[] args) throws Exception {
        //获取URL
        //URL url = new URL("https://www.jd.com");
        URL url = new URL("http://www.dangdang.com/");
        //下载资源
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"GB2312"));
        String msg=null;
        while (null !=(msg=br.readLine())){
            System.out.println(msg);
        }
        //分析
        //处理
    }
}