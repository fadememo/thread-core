package com.bfxy.thread.core.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UseExecutors {

	
	public static void main(String[] args) {
		
		//1 创建固定数量的线程池
		ExecutorService executorService1 = Executors.newFixedThreadPool(5);
		
		//2 单线程的线程池
		ExecutorService executorService2 = Executors.newSingleThreadExecutor();
		
		//3 创建一个没有容量限制的线程池
		ExecutorService executorService3 = Executors.newCachedThreadPool();
		
		//4 创建一个带有定时机制的线程池
		ScheduledExecutorService executorService4 = Executors.newScheduledThreadPool(1);
		
		executorService4.scheduleWithFixedDelay(new Task(1), 5, 2, TimeUnit.SECONDS);
		
		
		
		
	}
	
}
