package com.majoy.thread.core.test;

class MultiThread extends Thread{
    private String name;
    public MultiThread() {

    }

    public MultiThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        //调取的是父类的方法,输出:当前运行线程为Thread-0
        System.out.println("当前运行线程为"+this.getName()+",骚操作是 ");
        //调取的是子类的成员变量 输出:当前运行线程为t1
        System.out.println("当前运行线程为"+this.name+",骚操作是 ");
    }
    /*通过以下程序实例，可以很容易的区分出start()方法和run()方法的区别:
            t.start(); 该行代码相当于是启动线程，
            t.run(); 该行代码相当于是使用t这个类中的run方法而已.*/
    public static void main(String[] args) {
        Thread t1 = new MultiThread("t1");
        t1.run();
        System.out.println("run之后");
        t1.start();
        System.out.println("start之后");
    }
}
