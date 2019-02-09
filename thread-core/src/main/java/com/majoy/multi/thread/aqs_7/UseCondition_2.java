package com.majoy.multi.thread.aqs_7;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 整理一下范例代码的逻辑:
 * 建立一把锁,获得锁的condition,通过两个线程去执行await和signal;先后执行
 *
 *
 */
public class UseCondition_2 {
    //建立锁
    private ReentrantLock lock = new ReentrantLock();
    //获取判断信号,
    Condition condition = lock.newCondition();

    /**
     * 执行await
     */
    public void process1(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"当前线程释放锁~~~await释放,难道unllock?");
            condition.await();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行??");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 执行
     */
    public void process2(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"当前线程唤醒通知~~~");
            condition.signal();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行??");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        UseCondition_2 uc = new UseCondition_2();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process1();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process2();
            }
        },"t2");
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

}
