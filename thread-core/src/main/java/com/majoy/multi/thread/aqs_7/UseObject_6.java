package com.majoy.multi.thread.aqs_7;

/**
 * 总结:可以未经过wait先notify,但是会导致wait无限期等待.
 */
public class UseObject_6 {
    Object obj= new Object();

    public int process(){
        synchronized (obj){
            int sum = 0;
            for(int i=0;i<10;i++){
                sum+=i;
            }
            return sum;
        }
    }
    public void pWait(){
        synchronized (obj){
            try {
                System.out.println("进入waite区");
                //obj.notifyAll();
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行等待");
    }
    public void pNotify(){
        synchronized (obj){
           obj.notify();
        }
    }

    public static void main(String[] args) {
        UseObject_6 uo = new UseObject_6();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                uo.process();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uo.pWait();
                System.out.println("完成wait");

            }
        });
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        uo.pNotify();



    }





}
