package com.bfxy.thread.core.pool;

public class Task implements Runnable {

	private int taskId;
	
	public Task(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		System.err.println("run task id : " + this.taskId);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "当前线程DI: " + this.taskId;
	}

}
