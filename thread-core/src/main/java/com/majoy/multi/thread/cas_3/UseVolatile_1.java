package com.majoy.multi.thread.cas_3;

import java.util.ArrayList;
import java.util.List;

public class UseVolatile_1 extends  Thread{
    private static List<String> list = new ArrayList();
    private volatile  boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void run(){
        System.out.println("进入Run方法");
        while(isRunning==true){
            //
        }
        System.out.println("线程终止");
    }

    public static void main(String[] args) {
        UseVolatile_1 uv1 = new UseVolatile_1();
        //volatile修饰的变量的会自动通知到其他引用该变量的线程
        uv1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        uv1.setRunning(false);
        System.out.println("当前的值为"+false);
    }


}
