package com.bfxy.thread.core.design.masterworker;

import java.util.Random;

public class Main {

	
	public static void main(String[] args) {
		
		System.err.println("线程数:" + Runtime.getRuntime().availableProcessors());
		Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors());
		
		Random r = new Random();
		
		for(int i = 0; i < 100; i ++) {
			Task t = new Task(i, r.nextInt(1000));
			master.submit(t);
		}
		
		master.execute();
		
		long start = System.currentTimeMillis();
		
		//CountDownLatch
		
		while(true) {
			if(master.isComplete()) {
				long end = System.currentTimeMillis();
				int result = master.getResult();
				System.err.println("最终执行结果: " + result + ", 总耗时: " + (end - start));
				break;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
	}
}
