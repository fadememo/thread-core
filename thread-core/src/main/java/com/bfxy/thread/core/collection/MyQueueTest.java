package com.bfxy.thread.core.collection;

public class MyQueueTest {

	
	public static void main(String[] args) throws Exception {
		
		
		MyQueue mq = new MyQueue(5);
		
		mq.put("a");
		mq.put("b");
		mq.put("c");
		mq.put("d");
		mq.put("e");
		
		System.err.println("当前元素的个数: " + mq.size());
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mq.put("f");
				mq.put("g");
			}
		}, "t1");
		
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					Thread.sleep(1000);
					Object o1 = mq.take();
					
					Thread.sleep(1000);
					Object o2 = mq.take();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t2");
		
		
		t1.start();
		Thread.sleep(1000);
		t2.start();
		
		Thread.sleep(5000);
		System.err.println(mq.getQueueList().toString());
		
		
		
		
		
		
	}
}
