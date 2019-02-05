package com.bfxy.thread.core.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UseExchanger {
	
	private static final Exchanger<String> exchanger = new Exchanger<>();

	private static ExecutorService executorService = Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) {
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				String A = "银行流水A";
				try {
					String B = exchanger.exchange(A);	//交换我自己的数据 并且获取别人的数据
				
					System.err.println("线程A: " + B);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		});
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				String B = "银行流水B";
				try {
					String A = exchanger.exchange(B);	//交换我自己的数据 并且获取别人的数据
					
					System.err.println("线程B: " + A);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
		});
		executorService.shutdown();
	}
	
	
	
	
	
	
	
	
	
	
}
