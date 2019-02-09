package com.bfxy.thread.core.aqs;

public class UseObjectLock {
    Object lock = new Object();
    public int process(){
        synchronized (lock) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            return sum;
        }
    }
    public void pwait(){
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行等待");
    }
    public void pnotify(){
        synchronized (lock) {
            lock.notify();
        }
    }
    public static void main(String[] args) throws Exception {
        UseObjectLock uo = new UseObjectLock();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(uo.process());
                uo.pnotify();
                System.out.println("唤醒已经执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uo.pwait();
            }
        });
        A.start();
        //后阻塞:
        Thread.sleep(1000);

    }
}
