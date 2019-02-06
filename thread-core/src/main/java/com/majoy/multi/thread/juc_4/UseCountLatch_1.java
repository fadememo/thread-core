package com.majoy.multi.thread.juc_4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程阻塞,等待其他多个线程执行完通知的场景
 * 主要方法是:latch.await() 和 latch.countDown();
 */
public class UseCountLatch_1 {
    private static CountDownLatch latch = new CountDownLatch(2);

    /**
     * CountDownLatch 必须用在多线程并发下,首次缩写代码是常规串行所以没有体现特点
     * @param args
     */
    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("主线程已经就位,等待");
                try {
                    latch.await(50,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("所有线程已经准备就绪,开始");
            }
        });
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 发出信号1");
                latch.countDown();
                latch.countDown();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 发出信号2");
                latch.countDown();
                latch.countDown();
            }
        });
        t.start();
        t1.start();
        t2.start();

    }



}
