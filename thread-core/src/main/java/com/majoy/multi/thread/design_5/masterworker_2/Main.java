package com.majoy.multi.thread.design_5.masterworker_2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟实现Master-Worker 模式,关键是三种角色
 * 任务分配者:Master,使用concurrentLinkedQueue
 * 任务执行者:Worker,内部存放任务队列的引用,存放结果集的引用
 *
 *
 * 分析:关键在于是否有多个master,拆分worker 的逻辑是什么,执行的任务有什么特点,结果怎么合并.
 */
public class Main {
    public static void main(String[] args) {
        Master master = new Master(new Worker(),10);
        Random r = new Random(10);
        for(int i=0;i<100;i++){
            Task t = new Task(i,r.nextInt(1000));
            master.submit(t);
            System.out.println(t);
        }
        long start = System.currentTimeMillis();
        long end = 0;
        Thread countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch latch = master.getLatch();
                System.out.println("latch.getCount() = "+latch.getCount());
                if(latch!=null){
                    try {
                        latch.await();
                        long end = System.currentTimeMillis();
                        System.out.println("总耗时:"+ (end - start) );
                        System.out.println("最终执行结果:"+ master.summary() );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("error!!!");
                }
            }
        },"countThread");

        countThread.start();
        master.execute();



    }








}
