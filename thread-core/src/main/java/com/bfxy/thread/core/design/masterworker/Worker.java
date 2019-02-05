package com.bfxy.thread.core.design.masterworker;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {

	private ConcurrentLinkedQueue<Task> taskQueue;
	
	private ConcurrentHashMap<String, Object> resultMap;
	
	public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public void run() {
		while(true) {
			Task task = this.taskQueue.poll();
			if(task == null) break;
			try {
				Object result = handle(task);
				this.resultMap.put(Integer.toString(task.getId()), result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 
	private  Random r = new Random();
	
	//实际做每一个工作!
	private Object handle(Task task) throws Exception {
		//每一个任务处理的时间是:
		//Thread.sleep(200 * r.nextInt(10));
		Thread.sleep(200);
		int ret = task.getCount();
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
