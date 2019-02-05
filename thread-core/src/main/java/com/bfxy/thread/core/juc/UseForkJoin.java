package com.bfxy.thread.core.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class UseForkJoin extends RecursiveTask<Integer> {
	
	private static final int THRESHOLD = 2;		//阈值
	
	private int start;
	
	private int end;
	
	public UseForkJoin(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		
		int sum = 0;	// 1 + 2 .... + 100
		
		boolean canCompute = (end - start) <= THRESHOLD;
		
		if(canCompute) {
			for(int i = start; i <=end; i++ ){
				sum += i;
			}
		} else {
			//如果说 任务数大于阈值的话, 就进行拆分 fork操作 然后去join 
			// 1 + 100 /2  = 50
			int middle = (start + end)/2;
			UseForkJoin leftTask = new UseForkJoin(start, middle);
			UseForkJoin rightTask = new UseForkJoin(middle+1, end);
			
			//执行左右两边的任务
			leftTask.fork();		
			rightTask.fork();	
			//等待任务执行完成后 进行获取结果
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();
			sum = leftResult + rightResult;
		}
		
		return sum;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		
		UseForkJoin ufj = new UseForkJoin(1, 100);
		
		Future<Integer> result = pool.submit(ufj);
		
		System.err.println("最终执行结果为:" + result.get());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
