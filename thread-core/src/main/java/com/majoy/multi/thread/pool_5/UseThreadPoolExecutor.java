package com.majoy.multi.thread.pool_5;

import java.util.concurrent.*;

public class UseThreadPoolExecutor {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,//int corePoolSize,
                3,//int maximumPoolSize,
                60,//long keepAliveTime,
                // TimeUnit unit
                TimeUnit.SECONDS,
                // BlockingQueue<Runnable> workQueue,
                //new ArrayBlockingQueue<>(2),
                new LinkedBlockingQueue<>(2),
                //ThreadFactory threadFactory,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "thread-");
                        //设置取消守护线程
                        if (t.isDaemon()) {
                            t.setDaemon(false);
                        }
                        //设置优先级
                        if (t.getPriority() != Thread.NORM_PRIORITY) {
                            t.setPriority(Thread.NORM_PRIORITY);
                        }
                        return t;
                    }
                },
                // RejectedExecutionHandler handler
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(Thread.currentThread().getName()+"已经被拒绝进入"+r.toString());
                    }
                }
        );
        /**
         * 总结:
         * 当同时提交的线程数少于 coreSize,则执行
         * 当同时提交的线程数 大于corsize,小于maxSize时:
         *      --如果使用的是 有界阻塞队列,那么队列未满时大于coresize的部分优先被阻塞队列挂起,剩余的被新建,
         *          直到超出maxSize.则执行拒绝策略
         *      --如果使用的是无界阻塞队列,则拒绝策略项(和maxsize)无用,线程会将所有超出coresize的线程挂起并执行
         * 另外:如果使用submit提交,则线程提交时已经经过future包装,不是你认识的那个弱爆小线程了
         */
        threadPoolExecutor.submit(new Task("t1"));
        threadPoolExecutor.submit(new Task("t2"));
        threadPoolExecutor.submit(new Task("t3"));
        threadPoolExecutor.submit(new Task("t4"));
        threadPoolExecutor.submit(new Task("t5"));
        threadPoolExecutor.submit(new Task("t6"));
        threadPoolExecutor.execute(new Task("t7"));
        threadPoolExecutor.execute(new Task("t8"));
        threadPoolExecutor.execute(new Task("t9"));
        threadPoolExecutor.execute(new Task("t10"));
        threadPoolExecutor.execute(new Task("t11"));
        threadPoolExecutor.shutdown();


    }






}
