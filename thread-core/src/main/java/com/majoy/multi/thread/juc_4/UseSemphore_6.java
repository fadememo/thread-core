package com.majoy.multi.thread.juc_4;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UseSemphore_6 {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ExecutorService service = Executors.newFixedThreadPool(100);
        Random random = new Random(49);
        for(int i = 0;i<30;i++){
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"进入访问状态~~");
                        Thread.sleep(1000*(random.nextInt(10)) );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                        //此处如果顺序错就会出现有六个线程进入的错觉,
                        System.out.println(Thread.currentThread().getName()+"退出 ~~");
                        System.out.println(semaphore.getQueueLength());//输出的是等待的线程个数,为什么期初是4?
                        semaphore.release();
                    }
                }
            });

        }

        service.shutdown();

    }
}
