package com.majoy.multi.thread.pool_5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task implements Runnable{
    private String name;

    public Task() {
    }
    public Task(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" "+this.name+" " + " 执行完毕~~~~~");
    }
}

/**
ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>())
FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()))
ScheduledThreadPoolExecutor(corePoolSize)
 */
public class UseExecutors {
    public static void main(String[] args) {
        /**
         * 创建固定数量的线程线程池,实际返回的是ThreadPoolExecutor
         * 如果提交个数超出线程池大小,会第二批进入
         *  ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
         * execute()和submit()
         * 为什么这里先输出 isshutdown然后线程执行结果才输出???
         */
        ExecutorService service1 = Executors.newFixedThreadPool(5);
        try {
            service1.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0 ;i<6;i++){
            //service1.submit(new Task("t"+i));
            //service1.execute(new Task("t"+i));
        }
        service1.shutdown();
        System.out.println(service1.isTerminated());
        System.out.println(service1.isShutdown());
        System.out.println("*******************************************");

        /**
         * 创建一个单线程华的线程池,只会用唯一的工作线程来执行任务
         * FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()))
         */
        ExecutorService  service2 = Executors.newSingleThreadExecutor();
        for(int i=0;i<3;i++){
            //service2.submit(new Task("t"+i));
        }
        service2.shutdown();

        /**
         * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         *  ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>())
         */
        //System.out.println(0x7f);
        ExecutorService service3 = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            //service3.submit(new Task("t"+i));
        }
        service3.shutdown();

        /**
         * 创建一个定长线程池，支持定时及周期性任务执行
         * ScheduledThreadPoolExecutor(corePoolSize)
         */
        ScheduledExecutorService service4 = Executors.newScheduledThreadPool(2);
        for(int i=0;i<5;i++){
            //每过两秒执行一次
            //service4.scheduleWithFixedDelay(new Task("t"),1,2,TimeUnit.SECONDS);
            //service4.submit(new Task("t"+i));
            service4.scheduleAtFixedRate(new Task("t"),1,2,TimeUnit.SECONDS);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service4.shutdown();

    }



}
