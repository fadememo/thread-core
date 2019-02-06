package com.majoy.multi.thread.juc_4;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 设计案例:
 * 使用future模式实现之前cyclibarrier的效果
 */
public class UseFuture_3 implements Callable<String>{
    private String param;
    private static AtomicInteger aI = new AtomicInteger(3);
    private static long start;

    public UseFuture_3() {
    }
    public UseFuture_3(String param) {
        this.param = param;
    }


    @Override
    public String call() throws Exception {
        //int i = aI.get();//这里ms会有并发问题?
        Thread.sleep(aI.get()*1000);//模拟程序执行耗时
        String result = this.param + "执行中   ,计数为 " + aI.get() ;
        //这里同时获得值为3 ,该怎么解决该问题?互斥可以,还有呢?
        System.out.println(this.param  + aI.get() +(System.currentTimeMillis()-start));
        System.out.println("修改?" +aI.addAndGet(2));
        return result;
    }


    public static void main(String[] args) {
        start = System.currentTimeMillis();
        System.out.println("start = "+start);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //
        Callable<String>call1 = new UseFuture_3("1号选手");
        Callable<String>call2 = new UseFuture_3("2号选手");
        Callable<String>call3 = new UseFuture_3("3号选手");
        //
        FutureTask<String> futureTask1 = new FutureTask<>(call1);
        FutureTask<String> futureTask2 = new FutureTask<>(call2);
        FutureTask<String> futureTask3 = new FutureTask<>(call3);
        //
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);
        executorService.submit(futureTask3);
        System.out.println("同时并行主线程本身业务");
        try {
            Thread.sleep(5000);
            System.out.println("处理完成 "+futureTask1.get()+(System.currentTimeMillis()-start));
            System.out.println("处理完成 "+futureTask2.get()+(System.currentTimeMillis()-start));
            System.out.println("处理完成 "+futureTask3.get()+(System.currentTimeMillis()-start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) );
        /**
         * 分析:理论上,如果是cyclibarrier模式,则需要18s
         * 而future模式,只需要15s,省去了主线程等待就绪时间
         */

    }
}
