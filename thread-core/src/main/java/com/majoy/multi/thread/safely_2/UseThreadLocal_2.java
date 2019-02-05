package com.majoy.multi.thread.safely_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UseThreadLocal_2 {
    //静态的成员变量,会被多个线程复制出多个线程版本
    public static ThreadLocal<List> threadLocal = new ThreadLocal<>();
    //threadLoacl 作为载体对set方法和get方法是透明的
    public List getThreadLocal() {
        return threadLocal.get();
    }

    public void setThreadLocal(List list) {
        threadLocal.set(list);
    }

    public static void main(String[] args) {
        UseThreadLocal_2 u = new UseThreadLocal_2();
        ArrayList<String>arrayList = new ArrayList<>(Arrays.asList("majoy1","majoy2","majoy3"));
        u.setThreadLocal(arrayList);//在这里进行了初始化,但是副本成员变量为引用类型为空
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //NullPointerException ,
                //u.getThreadLocal().add("majoy5");
                u.setThreadLocal(Arrays.asList("test1"));
                System.out.println("t1: "+u.getThreadLocal());
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //u.getThreadLocal().remove(2);
                u.setThreadLocal(Arrays.asList("test2"));
                System.out.println("t2 "+u.getThreadLocal());
            }
        },"t2");
        t1.start();
        t2.start();
        //t2.notify();
        //t2.wait();
        System.out.println("main: "+ u.getThreadLocal());
        //System.out.println("t1: "+ u.getThreadLocal(t1));
        // 无法获取其他线程的数据
        //System.out.println("t2: "+ u.getThreadLocal());



    }


}
