package com.bfxy.thread.core.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UseCondition {

	
	//现在有一把锁
	private Lock lock = new ReentrantLock();	
	
	//synchronized  wait ---- notify
	//基于这把锁产生了一个 condition: 作用是对于这把锁的 唤醒 和 等待操作
	private Condition condition = lock.newCondition();
	
	public void method1(){
		lock.lock();
		try {
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
			Thread.sleep(3000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
			condition.await();
			System.out.println("当前线程：" + Thread.currentThread().getName() +"继续执行...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.err.println(Thread.currentThread().getName() + " unlock");
			lock.unlock();
		}
	}
	
	public void method2(){
		lock.lock();
		try {
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
			Thread.sleep(3000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");
			condition.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws Exception {
		final UseCondition uc = new UseCondition();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				uc.method1();
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				uc.method2();
			}
		}, "t2");
		t1.start();
		Thread.sleep(1);
		t2.start();
	}
	
	
	
}
