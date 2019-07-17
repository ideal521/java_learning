package com.sxt.server.basic;

import java.lang.reflect.InvocationTargetException;

/**
 * @author lyl3878
 * @date 6/30/2019
 */
public class ReflectTest {
    public static void main(String[] args) {
        try {
            // 三种方式
            // 1、对象.getClass()
            Iphone iphone = new Iphone();
            Class clz= iphone.getClass();
            // 2、类.class()
            clz = Iphone.class;
            // 3、Class.forName("包名.类名");
            clz = Class.forName("com.sxt.server.basic.Iphone");


            Iphone iphone2 = (Iphone) clz.newInstance();
            System.out.println(iphone2);

            Iphone iphone3 = (Iphone)clz.getConstructor().newInstance();
            System.out.println(iphone3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

class Iphone{
    public Iphone(){}
}
