package com.bfxy.thread.core.aqs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {

	private ReentrantLock reentrantLock = new ReentrantLock();
	
	public void method() {
		reentrantLock.lock();
		try {
			System.err.println("当前线程: " + Thread.currentThread().getName() + "进入...");
			Thread.sleep(2000);
			System.err.println("当前线程: " + Thread.currentThread().getName() + "退出...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		UseReentrantLock useLock = new UseReentrantLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				useLock.method();
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				useLock.method();
			}
		}, "t2");
		
		t1.start();
		t2.start();
		
		
	}
	
	
	
	
}
