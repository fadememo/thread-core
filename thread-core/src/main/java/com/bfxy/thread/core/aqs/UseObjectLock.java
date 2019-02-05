package com.bfxy.thread.core.aqs;

public class UseObjectLock {

    public static void main(String[] args) throws Exception {
    	Object lock = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
                System.out.println(sum);
            }
        });
        A.start();
        //后阻塞:
        Thread.sleep(1000);
        synchronized (lock) {
			lock.notify();
		}
    }
}
