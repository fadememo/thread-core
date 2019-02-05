package com.majoy.multi.thread.safely_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MsssageTransfer_3 {
    //使用线程同步,不使用volatile 举例方法为m1 m2 m3
    //private volatile  static List<String>list = new ArrayList<>();
    //使用线程同步,使用volatile 举例方法为m4
    private volatile  static List<String>list = new ArrayList<>();

    public void add(String s){
        System.out.println(Thread.currentThread().getName()+" 在位置 "+list.size()+" 放入元素 "+s);
        list.add(s);
        if(list.size() == 5){
            System.out.println(Thread.currentThread().getName()+"通知已经发出");
        }
    }
    public boolean send(){
        if(list.size()==5){
            System.out.println(Thread.currentThread().getName()+"接收通知");
            return true;
        }else{
            return false;
        }
    }
    public int size(){
        return list.size();
    }
    public static void main(String[] args) {
        MsssageTransfer_3 m = new MsssageTransfer_3();
        /**
         * m1()会由于时间片分配的偶然性导致不一定能保证B发出通知
         */
        //m.m1();
        /**
         * 使用synchronized 和wait notify方法,如果不使用同一个锁,会报锁异常
         */
        //m.m2();
        /**
         *latch.countDown();//条件符合,或者说副线程执行完毕,发送通知
         *latch.await();//主线程进入等待状态
         */
        //m.m3();
        /**
         * 使用volatile关键字
         */
        m.m4();

    }

    public void m1() {
        System.out.println(Thread.currentThread());//Thread[main,5,main]
        MsssageTransfer_3 m = this;
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    m.add("majoy"+i);
                }
            }
        },"A");
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(m.send()){
                        break;
                    };
                }
            }
        },"b");
        A.start();
        B.start();
        while(A.getState()!=Thread.State.TERMINATED||A.getState()!=Thread.State.TERMINATED){
            continue ;
        }
        System.out.println("结束");
    }
    public void m2() {
        System.out.println(Thread.currentThread());//Thread[main,5,main]
        MsssageTransfer_3 m = this;
        Object lock = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        for(int i = 0;i<10;i++){
                            m.add("majoy"+i);
                            Thread.sleep(500);
                            if(m.size()==5){
                                System.err.println("已经发出了唤醒通知!");
                                this.notify();
                            }
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A");
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    synchronized (this){
                        if(m.size()==5){

                        }else{
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        };
                    }
                }
            }
        },"b");
        A.start();
        B.start();
        while(A.getState()!=Thread.State.TERMINATED||A.getState()!=Thread.State.TERMINATED){
            continue ;
        }
        System.out.println("结束");
    }
    public void m3() {
        System.out.println(Thread.currentThread());//Thread[main,5,main]
        MsssageTransfer_3 m = this;
        //唯一的latch,不可变应该作为 final
        final CountDownLatch latch = new CountDownLatch(1);
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    m.add("majoy"+i);
                }
            }
        },"A");
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(m.send()){
                        break;
                    }else{

                    };
                }
            }
        },"b");
        A.start();
        B.start();
        while(A.getState()!=Thread.State.TERMINATED||A.getState()!=Thread.State.TERMINATED){
            continue ;
        }
        System.out.println("结束");
    }
    public void m4() {
        System.out.println(Thread.currentThread());//Thread[main,5,main]
        MsssageTransfer_3 m = this;
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    m.add("majoy"+i);
                }
            }
        },"A");
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(m.send()){
                        break;
                    };
                }
            }
        },"b");
        A.start();
        B.start();
        while(A.getState()!=Thread.State.TERMINATED||A.getState()!=Thread.State.TERMINATED){
            continue ;
        }
        System.out.println("结束");
    }



}
