package com.majoy.multi.thread.juc_4;

import java.util.concurrent.*;

/**
 * CyclicBarrier 的主要方法在于构造和barrier.await()
 * 适用于多个线程挂起等待全部就绪后执行,由最后一个await()唤醒
 * 当有多个barrier时要注意,会互相干扰
 */
public class useCycliBarrier_2 {
    private static CyclicBarrier barrier ;

    public static void main(String[] args) {
        barrier = new CyclicBarrier(3);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //do sth
                try {
                    Thread.sleep(1000);
                    System.out.println("t1准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t1开始跑步");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //do sth
                try {
                    Thread.sleep(3000);
                    System.out.println("t2准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t2开始跑步");
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                //do sth
                try {
                    Thread.sleep(7000);
                    System.out.println("t3准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t3开始跑步");
            }
        });
        t1.start();
        t2.start();
        t3.start();
        /*********************************************************
         * 换线程池来实现
         */
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CyclicBarrier barrier2 = new CyclicBarrier(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println("t4准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t4开始跑步");
            }
        },barrier2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("t5准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t5开始跑步");
            }
        },barrier2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                    System.out.println("t6准备完毕");
                    barrier.await(50, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("一切就绪,t6开始跑步");
            }
        },barrier2);
        executorService.shutdown();


    }



}
