package com.majoy.multi.thread.juc_4;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * exchanger 适用于 两个线程在一个交叉点交换数据
 */
public class UseExchange_4 {
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行");
                String ret0 = "thread-0 执行结果";
                String ret = null;
                try {
                    ret = exchanger.exchange(ret0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread-0 输出: " +ret);
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行");
                String ret1 = "thread-1 执行结果";
                String ret = null;
                try {
                    ret = exchanger.exchange(ret1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread-1 输出: " +ret);
            }
        });
        service.shutdown();



    }




}
