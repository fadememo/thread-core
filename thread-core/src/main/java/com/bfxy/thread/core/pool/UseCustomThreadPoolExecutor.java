package com.bfxy.thread.core.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCustomThreadPoolExecutor extends ThreadPoolExecutor {

	public UseCustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		System.err.println("-------线程执行之前----------");
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		System.err.println("-------线程执行之后----------");
	}
	
	
	
	public static void main(String[] args) {
		
		
		UseCustomThreadPoolExecutor uctpe = new UseCustomThreadPoolExecutor(1, 2, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
		
		uctpe.execute(new Task(1));
		
	}
	
	
	
	
	
	
	
	
	
	


}
