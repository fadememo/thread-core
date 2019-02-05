package com.bfxy.thread.core.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UseSemaphore {

	
	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		
		Semaphore semaphore = new Semaphore(5);
		
		for(int index = 1; index <= 20; index ++){
			final int token = index;
			
			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
						//进行相关的业务操作
						System.err.println("获得许可,执行操作..." + token);
					    long sleepTime = (long)(Math.random() * 10000);
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						semaphore.release();
					}
				}
			};
			
			executorService.execute(run);
		}
		
		
		//semaphore.getQueueLength() 获取的是什么长度？
		//System.err.println("queue length: " + semaphore.getQueueLength());
		
		executorService.shutdown();
		
		
		
	}
}
