package com.bfxy.thread.core.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class UseFuture implements Callable<String> {

	private String param;
	
	public UseFuture(String param){
		this.param = param;
	}
	
	@Override
	public String call() throws Exception {
		//模拟执行业务逻辑的耗时
		Thread.sleep(3000);
		String result = this.param + ", 处理完成!";
		return result;
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		String queryStr1 = "query1";
		String queryStr2 = "query2";
		FutureTask<String> future1 = new FutureTask<String>(new UseFuture(queryStr1));
		
		FutureTask<String> future2 = new FutureTask<String>(new UseFuture(queryStr2));
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		executorService.submit(future1);	//异步的操作
		executorService.submit(future2);	//异步的操作
		
		System.err.println("处理其他相关的任务...");
		Thread.sleep(2000);
		
		String ret1 = future1.get();
		String ret2 = future2.get();
		
		//guava
		
		System.err.println("数据处理完成: " + ret1);
		System.err.println("数据处理完成: " + ret2);
	}
}
