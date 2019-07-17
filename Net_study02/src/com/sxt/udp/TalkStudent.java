package com.sxt.udp;

/**
 * 加入多线程，实现双向交流 模拟在线咨询
 *
 * @author lyl3878
 * @date 7/17/2019
 */
public class TalkStudent {
    public static void main(String[] args) {
        new Thread(new TalkSend(7777, "localhost", 9999)).start(); // 发送

        new Thread(new TalkReceive(8888,"老师")).start(); // 接收
    }
}
