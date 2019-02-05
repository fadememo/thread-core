package com.bfxy.thread.core.cas;

public class UseVolatile extends Thread {

	private volatile boolean isRunning = true;
	
	
	private void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void run(){
		System.err.println("进入run方法...");
		while(isRunning == true) {
			//....
		}
		System.err.println("线程停止!");
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		UseVolatile uv = new UseVolatile();
		uv.start();
		
		Thread.sleep(2000);
		//修改isRunning = false
		uv.setRunning(false);
		
		System.err.println("isRunning的值已经被设置成了false!");
		}
	
	
}
