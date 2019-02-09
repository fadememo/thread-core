package com.majoy.multi.thread.aqs_7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock,默认构造方法返回的是非公平锁, 成员有
 * :静态内部类 Sync,FairSync,UnfairSync,
 * 这些内部类主要用于锁机制,获得锁和释放锁,检查锁状态等
 * 非公平UnfairSync 和 公平FairSync 的区别在于
 * 公平锁方法会额外执行 !hasQueuedPredecessors() ,false的情况下继续执行
 hasQueuedPredecessors() 方法在阻塞队列有线程存在&&(首位阻塞节点线程为空||首位被阻塞的线程不是当前线程) 返回true
 如果没有被阻塞的线程或者首位阻塞的线程是当前线程 返回false
 *
 */
public class UseReentrantLock_1 {
    private ReentrantLock lock = new ReentrantLock();
    public void process(){
        /**
         * 假设我将lock()放到try 和不放到try有什么区别?
         * 后者 假如异常发生在 lock()执行后,try前;未能成功进入finally去 unlock
         * 前者 假如异常发生在lock时,去执行unlock  ,所以上锁代码要写到 try外面
         */
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+" 开始执行 "+Thread.currentThread().getId());
            Thread.sleep(1000);
            System.out.println("线程"+Thread.currentThread().getName()+" 退出 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void show(){
        System.out.println("lock.tryLock() "+lock.tryLock());
        System.out.println("getQueueLength() "+lock.getQueueLength());
        System.out.println("getHoldCount() "+lock.getHoldCount());
        System.out.println("hasQueuedThreads() "+lock.hasQueuedThreads());
        System.out.println("isFair() "+lock.isFair());
        System.out.println("isHeldByCurrentThread() "+lock.isHeldByCurrentThread());
        System.out.println("isLocked() "+lock.isLocked());
    }

    public static void main(String[] args) {
        /* 运算符优先级
        int i = 0;
        System.out.println(i==3||(i=3)==2);//false
        System.out.println((i=3)==2||i==3);//true*/
        /**
         * 下面的执行案例 当线程池线程个数限制为3,则会
         */
        ExecutorService service = Executors.newFixedThreadPool(3);
        UseReentrantLock_1 ur = new UseReentrantLock_1();
        CountDownLatch latch = new CountDownLatch(6);
        ReentrantLock lock = ur.lock;
        for(int i = 0;i<2;i++){
            service.submit(new Runnable() {
                @Override
                public void run() {
                    /*ur.show();
                    System.out.println("**************");*/
                    ur.process();
                   /* lock.lock();
                    System.out.println("**************");
                    ur.show();*/
                }
            });
        }
        service.shutdown();


    }
}
