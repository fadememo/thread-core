package com.majoy.thread.core.ShareVo;

public class TestThread implements Runnable{
    private String name;
    public static int count;
    @Override
    public void run() {
        System.out.println("当前线程为"+this.name+";计数为"+(count++));
    }
}
