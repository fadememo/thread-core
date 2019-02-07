package com.bfxy.thread.core.pool;

import java.util.concurrent.*;


public class UseThreadPoolExecutor {

	
	public static void main(String[] args) {
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1,	// corePoolSize: 核心线程数,线程池初始化的时候就会被创建
				3,	// maximumPoolSize: 线程池的最大上限	//在使用无界队列的时候, 此参数 不起作用
				60,	//线程的存活时间
				TimeUnit.SECONDS,
				//workQueue：BlockingQueue接口下面的实现类
				new ArrayBlockingQueue<>(2),	//使用有界队列: ArrayBlockingQueue
				//new LinkedBlockingQueue<>(),	//使用无界队列: LinkedBlockingQueue
				new ThreadFactory() {	//threadFactory 线程工厂, 用于获取一个新的线程, 然后把该线程 投递到我们的线程池中去
					@Override
					public Thread newThread(Runnable r) {
						Thread th = new Thread(r, "order-thread");
						if(th.getPriority() != Thread.NORM_PRIORITY) {
							th.setPriority(Thread.NORM_PRIORITY);
						}
						if(th.isDaemon()) {
							th.setDaemon(false);
						}
						return th;
					}
				},	
				
				//使用无界队列时, 拒绝策略不起到作用
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.err.println("当前的任务已经被拒绝: " + r.toString());
					}
				});
		
		Task t1 = new Task(1);
		Task t2 = new Task(2);
		Task t3 = new Task(3);
		Task t4 = new Task(4);
		Task t5 = new Task(5);
		Task t6 = new Task(6);
		
		/**
		//线程池提交任务的方法:
		pool.execute(t1);  		//execute: 如果你的任务没有返回值, 则使用该方法提交任务
		pool.submit(t1);		//submit: 如果你的任务有返回值, 则使用该方法提交任务, 返回一个Future对象(Future模式)
		*/
		
		/**
		 * 
		 * 在使用有界队列时:
		 * 1 若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程
		 * 2 若大于corePoolSize，则会将任务加入队列
		 * 3 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程
		 * 4 若线程数大于maximumPoolSize，则执行拒绝策略。
		 */
		
		// 1 若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程
		pool.execute(t1);	//core size = 1  t1任务会被核心线程执行
		// 2 若大于corePoolSize，则会将任务加入队列
		pool.execute(t2);	// 有界队列容量为: 2
		pool.execute(t3);
		// 3 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程, 并执行该任务
		pool.execute(t4);	// 线程池中的总线程数 2  , maximumPoolSize = 3 
		pool.execute(t5);	// 线程池中的总线程数 3  , maximumPoolSize = 3 
		// 4 若线程数大于maximumPoolSize，则执行拒绝策略。
		pool.execute(t6);
		
		
		pool.shutdown();
		
		
		
		
		
		
		
		
		
		
	}
}
