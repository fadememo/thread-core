package com.bfxy.thread.core.aqs;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class UseReadWriteLock {

	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private ReadLock readLock = rwLock.readLock();
	private WriteLock writeLock = rwLock.writeLock();
	
	public void read(){
		readLock.lock();
		try {
			System.err.println("当前线程 " + Thread.currentThread().getName() + "进入了读方法");
			Thread.sleep(3000);
			System.err.println("当前线程" + Thread.currentThread().getName() + "退出了读方法");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}
	
	public void write(){
		writeLock.lock();
		try {
			System.err.println("当前线程 " + Thread.currentThread().getName() + "进入了写方法");
			Thread.sleep(3000);
			System.err.println("当前线程" + Thread.currentThread().getName() + "退出了写方法");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		UseReadWriteLock rwLock = new UseReadWriteLock();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				rwLock.read();
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				rwLock.read();
			}
		}, "t2");
		
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				rwLock.write();
			}
		}, "t3");
		
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
