package com.bfxy.thread.core.design.masterworker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

	//1 承装任务的一个容器：
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();
	
	//2 承装worker执行器
	private HashMap<String, Thread> workers = new HashMap<>();
	
	//3 接受worker处理成功的结果集合
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
	
	//4 构造方法里面,要对worker进行一个初始化操作
	public Master(Worker worker, int workCount) {
		//4.1 每一个worker 应该有master里 任务队列容器的引用
		worker.setTaskQueue(this.taskQueue);
		//4.2 每一个worker 应该有master里 结果集容器的引用
		worker.setResultMap(this.resultMap);
		//4.3 我们把所有的worker进行初始化 放入 workers容器中
		for(int i = 0; i < workCount; i ++){
			this.workers.put(Integer.toString(i), new Thread(worker));
		}
	}
	
	//5 需要一个提交任务的方法
	public void submit(Task task) {
		this.taskQueue.add(task);
	}
	
	//6 需要有一个真正让我们Master里的所有Worker进行工作的方法
	public void execute() {
		for(Map.Entry<String, Thread> me : this.workers.entrySet()) {
			me.getValue().start();
		}
	}
	
	//7 需要有一个统计的方法,用于合并结果集
	public int getResult() {
		int sum = 0;
		for(Map.Entry<String, Object> me : resultMap.entrySet()) {
			sum += (Integer)me.getValue();
		}
		return sum;
	}

	//8 判断是否所有的worker都完成工作了 如果完成返回true
	public boolean isComplete() {
		for(Map.Entry<String, Thread> me : this.workers.entrySet()) {
			if(me.getValue().getState() != Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
