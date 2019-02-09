package com.majoy.multi.thread.aqs_7;

import java.util.concurrent.locks.LockSupport;

/**
 * Locksupport的使用方法是使用静态方法来执行
 * 优点是随时可以park和unpark,并且 无先后顺序 限定
 */
public class UseLockSupport_7 {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0 ;
                for(int i = 0;i<10;i++){
                    sum+=i;
                }
                System.out.println("进行线程锁");
                LockSupport.park();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("退出线程锁");
            }
        },"t1");
        t1.start();
        LockSupport.unpark(t1);
        System.out.println("线程执行完毕");

    }
}
