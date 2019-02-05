package com.majoy.multi.thread.safely_2;

class  MyThread_2 extends Thread{
    private  int count = 5;
    public void run(){
        System.out.println(Thread.currentThread().getName()+ "变量 " + count);
        count --;
    }
}


public class MyThread_1 implements Runnable{
    private  int count = 5;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ "变量 " + count);
        count --;
    }

    public static void main(String[] args) {
        MyThread_1 t = new MyThread_1();
        new Thread(t,"t1").start();
        new Thread(t,"t2").start();
        new Thread(t,"t3").start();
        new Thread(t,"t4").start();
        new Thread(t,"t5").start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyThread_1 is over");
        MyThread_2 t2 = new MyThread_2();
        new Thread(t2,"T1").start();
        new Thread(t2,"T2").start();
        new Thread(t2,"T3").start();
        new Thread(t2,"T4").start();
        new Thread(t2,"T5").start();
        //为什么继承Thread会导致 线程安全问题,而如那边了不会

    }
}
