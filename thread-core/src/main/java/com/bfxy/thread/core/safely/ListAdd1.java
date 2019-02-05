package com.bfxy.thread.core.safely;

import java.util.ArrayList;
import java.util.List;

public class ListAdd1 {

	// 1 定义的承装字符串的容器
	private static List list = new ArrayList();	
	
	// 2 追加方法
	public void add(){
		list.add("bfxy");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final ListAdd1 list1 = new ListAdd1();
		
		final Object lock = new Object();
		
		// 线程A
		Thread A = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
						for(int i = 0; i <10; i++){
							list1.add();
							System.out.println("当前线程：" + Thread.currentThread().getName() + ", 添加了一个元素..");
							Thread.sleep(500);
							if(list.size() == 5) {
								System.err.println("已经发出了唤醒通知!");
								lock.notify();
							}
						}						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "A");
		
		
		// 线程B
		Thread B = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					synchronized (lock) {
						if(list1.size() != 5) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					//if(list1.size() == 5){
						System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
						//throw new RuntimeException();
					//}
				}
			}
		}, "B");	
		
		B.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		A.start();
		
	}
	
	
}
