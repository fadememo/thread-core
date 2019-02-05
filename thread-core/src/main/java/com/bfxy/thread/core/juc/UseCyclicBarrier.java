package com.bfxy.thread.core.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UseCyclicBarrier {

	
	static class Runner implements Runnable {
		
		private String name;
		
		private CyclicBarrier cyclicBarrier;
		
		public Runner(String name, CyclicBarrier cyclicBarrier){
			this.name = name;
			this.cyclicBarrier = cyclicBarrier;
		}
		
		
		@Override
		public void run() {
			try {
				System.err.println("运动员：" + this.name + ", 进行准备工作!");
				Thread.sleep(1000 * (new Random()).nextInt(10));
				System.err.println("运动员：" + this.name + ", 准备OK!");
				this.cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.err.println("运动员: " + this.name + ", GO! GO! GO!");
		}
		
	}
	
	
	public static void main(String[] args) {
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		executorService.submit(new Thread(new Runner("张三", cyclicBarrier)));
		executorService.submit(new Thread(new Runner("李四", cyclicBarrier)));
		executorService.submit(new Thread(new Runner("王五", cyclicBarrier)));
		
		executorService.shutdown();
		
	}
	
	
	
}
