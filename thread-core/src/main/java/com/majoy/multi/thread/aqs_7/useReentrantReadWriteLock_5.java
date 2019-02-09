package com.majoy.multi.thread.aqs_7;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * 总结:获取锁对象的方法是 lock.writeLock();
 *     使用的方法不依赖 condition,然后读读共享,写互斥.
 */
public class useReentrantReadWriteLock_5 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private WriteLock wLock= lock.writeLock();
    private ReadLock rLock = lock.readLock();

    public static void main(String[] args) {
        useReentrantReadWriteLock_5 ul = new useReentrantReadWriteLock_5();
        WriteLock wLock= ul.wLock;
        ReadLock rLock = ul.rLock;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                rLock.lock();
                try {
                    System.out.println("进入读共享");
                    Thread.sleep(1000);
                    System.out.println("退出读共享");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    rLock.unlock();
                }
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                wLock.lock();
                try {
                    System.out.println("进入写共享");
                    Thread.sleep(1000);
                    System.out.println("退出写共享");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    wLock.unlock();
                }
            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                rLock.lock();
                try {
                    System.out.println("进入读共享");
                    Thread.sleep(1000);
                    System.out.println("退出读共享");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    rLock.unlock();
                }
            }
        },"t3");
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                rLock.lock();
                try {
                    System.out.println("进入读共享");
                    Thread.sleep(1000);
                    System.out.println("退出读共享");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    rLock.unlock();
                }
            }
        },"t4");

        t1.start();
        t3.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        t4.start();
    }
}
