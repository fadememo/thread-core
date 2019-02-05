package com.bfxy.thread.core.juc;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {

	
	public static void main(String[] args) {
		
		CountDownLatch countDownLatch = new CountDownLatch(2);
		
		 Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.err.println("进入t1线程..");
				try {
					Thread.sleep(3000);	//做了一些初始化的准备..
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("t1线程初始化完毕, 通知t3 线程继续操作");
				countDownLatch.countDown();
			}
		}, "t1");
		 
		 Thread t2 = new Thread(new Runnable() {
				
			@Override
			public void run() {
				System.err.println("进入t2线程..");
				try {
					Thread.sleep(4000);	//做了一些初始化的准备..
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("t2线程初始化完毕, 通知t3 线程继续操作");
				countDownLatch.countDown();

			}
		}, "t2");
		 
		 Thread t3 = new Thread(new Runnable() {
				
			@Override
			public void run() {
				System.err.println("进入t3线程.., 并且进入等待...");
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("t3线程 进行后续的操作!");
				
			}
		}, "t3");
		 
		 t1.start();
		 t2.start();
		 t3.start();
		 
		 
		 
		 
		 
		 
		 
		 
		
		
	}
}
