package com.majoy.multi.thread.cas_3;

import com.bfxy.thread.core.cas.UseAtomic;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomic_3 {

    private static AtomicInteger count  = new AtomicInteger(10);
    private static int count2  = 100;
    public static void main(String[] args) {
        UseAtomic u = new UseAtomic();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(count.get()>0){
                    //System.out.println(Thread.currentThread().getName()+"   "+count );
                    //try {
                    //    Thread.sleep(10);
                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //}
                    //count--;
                    System.out.println(Thread.currentThread().getName()+"   "+count.getAndDecrement() );
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(count.get()>0){
                    //System.out.println(Thread.currentThread().getName()+"   "+count );
                    //try {
                    //    Thread.sleep(10);
                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //}
                    //count --;
                    System.out.println(Thread.currentThread().getName()+"   "+count.getAndDecrement() );
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(count.get()>0){
                    //System.out.println(Thread.currentThread().getName()+"   "+count );
                    //try {
                    //    Thread.sleep(10);
                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //}
                    //count --;
                    System.out.println(Thread.currentThread().getName()+"   "+count.getAndDecrement() );
                }
            }
        }).start();



    }




}
