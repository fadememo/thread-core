package com.majoy.multi.thread.pool_6;

import java.util.concurrent.*;

public class UseCustomExecutor_3 extends ThreadPoolExecutor {
    public UseCustomExecutor_3(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println("线程执行前~~~~");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        System.out.println("线程执行后~~~");
    }

    @Override
    protected void terminated() {
        super.terminated();
        System.out.println("线程池被终结");
    }

    public static void main(String[] args) {
        UseCustomExecutor_3  executor_3 = new UseCustomExecutor_3(1, 2, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(1), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("线程被拒绝");
            }
        });

        executor_3.submit(new Task("t1"));
        executor_3.submit(new Task("t2"));
        executor_3.submit(new Task("t3"));
        executor_3.submit(new Task("t4"));
        executor_3.submit(new Task("t5"));

        executor_3.shutdown();

    }
}
