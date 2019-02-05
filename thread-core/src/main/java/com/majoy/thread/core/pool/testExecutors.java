package com.majoy.thread.core.pool;

import com.bfxy.thread.core.pool.Task;

import java.util.concurrent.*;

public class testExecutors {
    /*
    *
    *public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), defaultHandler);
    }
    * */
    public static void main(String[] args) {
        //存在守护(代理委托)的单线程线程池
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        //???
        ExecutorService executor4 = Executors.newSingleThreadScheduledExecutor();;
        //带有定时机制的线程池
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(31);
        executor2.scheduleWithFixedDelay(new Task(1), 5, 2, TimeUnit.SECONDS);
        //固定数量的线程池
        ExecutorService executor3 = Executors.newFixedThreadPool(2);
        //无容量限制的线程池,回收空闲时间为60s
        ExecutorService executor5 = Executors.newCachedThreadPool();
    }
}
