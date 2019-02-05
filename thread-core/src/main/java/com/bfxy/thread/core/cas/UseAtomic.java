package com.bfxy.thread.core.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomic {

	private static /* int count = 0; */ AtomicInteger  count = new AtomicInteger(0);
	
	public synchronized int add(){
		//return count.addAndGet(10);
		count.addAndGet(3);  // + 3
		count.addAndGet(4);  // + 4
		count.addAndGet(2);  // + 2 
		count.addAndGet(1);  // + 1
		
		
		// 写了一段代码逻辑........ count.add(1);
		
		// 又写了一个复杂的逻辑...
		// count.add(1);
		// = 2
		
		return count.get();	
	}
	
	public static void main(String[] args) {
		UseAtomic ua = new UseAtomic();
		
		List<Thread> list = new ArrayList<>();
		
		//如果使用atomicIntger 最终的结果 一定是: 1000
		for(int i =0; i < 100; i++) {
			list.add(new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println("累计结果:" + ua.add());
				}
			}));
		}
		
		for(Thread t : list) {
			t.start();
		}
		
		
	}
	
	
}
