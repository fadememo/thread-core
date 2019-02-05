package com.bfxy.thread.core.safely;

public class MyThread extends Thread {

	private int count = 5;
	
	public synchronized void run(){
		count--;
		System.err.println(this.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		
		MyThread my = new MyThread();
		
		Thread t1 = new Thread(my, "t1");
		Thread t2 = new Thread(my, "t2");
		Thread t3 = new Thread(my, "t3");
		Thread t4 = new Thread(my, "t4");
		Thread t5 = new Thread(my, "t5");
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
	}
	
}
