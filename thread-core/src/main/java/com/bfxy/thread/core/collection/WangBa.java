package com.bfxy.thread.core.collection;

import java.util.concurrent.DelayQueue;

public class WangBa implements Runnable {

	private DelayQueue<WangMin> delayQeueu = new DelayQueue<WangMin>();
	
	public boolean start = true;	//表示网吧营业
	
	public void startMachine(String id, String name, int money) {
		WangMin wm = new WangMin(id, name, System.currentTimeMillis() + money * 1000);
		System.err.println("网名: " + name + ", 身份证: " + id + ", 缴费: " + money + "元, 开始上网!" );
		delayQeueu.add(wm);
	}
	
	public void overMachine(WangMin wm) {
		System.err.println("网名: " + wm.getName() + ", 身份证: " + wm.getId() + ", 已经到了下机时间!");
	}
	
	
	@Override
	public void run() {
		while(start) {
			try {
				WangMin wm = delayQeueu.take();
				overMachine(wm);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		WangBa wangb = new WangBa();
		System.err.println("网吧正常营业: ");
		Thread yingye = new Thread(wangb);
		yingye.start();

		wangb.startMachine("001", "张三", 2);
		wangb.startMachine("001", "李四", 5);
		wangb.startMachine("001", "王五", 10);
		
	}
	
	
	
	
	
	
	
	
	
	

}
