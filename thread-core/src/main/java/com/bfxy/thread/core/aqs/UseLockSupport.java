package com.bfxy.thread.core.aqs;

import java.util.concurrent.locks.LockSupport;

public class UseLockSupport {

    public static void main(String[] args) throws Exception {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
               
                try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				LockSupport.park();	//滞后的

                System.out.println(sum);
            }
        });
        A.start();
        //后阻塞:
        Thread.sleep(1000);
        LockSupport.unpark(A);	//优先的

    }
}
