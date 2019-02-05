package com.bfxy.thread.core.collection;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class UseBlockingQueue {

	
	public static void main(String[] args) throws Exception {
		
		
		//1.高性能的无阻塞无界队列:
		/**
		ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();
		clq.offer("a");
		clq.add("b");
		clq.add("c");
		clq.add("d");
		System.err.println("从头部取出元素：" + clq.poll());	//从头部取出一个元素,并且从容器本身移除
		System.err.println("容器长度: " + clq.size());
		System.err.println("从头部取出元素: " + clq.peek());	//从头部取出一个元素,单不会从容器中移除该元素
		System.err.println("容器长度: " + clq.size());
		*/
		
		//2. 基于阻塞 - 有界队列
		/**
		ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(5);
		abq.put("a");
		abq.add("b");
		abq.add("c");
		abq.add("d");		
		abq.add("e");
		System.err.println(abq.offer("f", 2, TimeUnit.SECONDS));
		ArrayBlockingQueue<String> abq2 = new ArrayBlockingQueue<>(5);
		abq.drainTo(abq2, 3);
		
		for (Iterator iterator = abq2.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.err.println("元素: " + string);
		}
		*/
		
		//3. 基于阻塞 - 无界队列
		//LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();
		
		
		
		//4. 不能盛放任何元素的 阻塞队列
		SynchronousQueue<String> sq = new SynchronousQueue<>();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.err.println("元素内容:" + sq.take());;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}, "t1").start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
					sq.add("a");
				
			}
		}, "t2").start();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
