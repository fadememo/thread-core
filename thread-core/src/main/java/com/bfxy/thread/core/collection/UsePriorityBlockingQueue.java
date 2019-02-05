package com.bfxy.thread.core.collection;

import java.util.concurrent.PriorityBlockingQueue;

public class UsePriorityBlockingQueue {

	
	public static void main(String[] args) throws InterruptedException {
		
		PriorityBlockingQueue<Node> pbq = new PriorityBlockingQueue<Node>();
		
		Node n3 = new Node(3, "node3");
		Node n4 = new Node(4, "node4");
		Node n2 = new Node(2, "node2");
		Node n1 = new Node(1, "node1");
		
		pbq.add(n4);
		pbq.add(n3);
		pbq.add(n1);
		pbq.add(n2);
		
		System.err.println("0 容器为: " + pbq);
		System.err.println("1 获取元素: " + pbq.take().getId());
		System.err.println("1 容器为: " + pbq);
		System.err.println("2 获取元素: " + pbq.take().getId());
		System.err.println("2 容器为: " + pbq);
		System.err.println("3 获取元素: " + pbq.take().getId());
		System.err.println("3 容器为: " + pbq);
		System.err.println("4 获取元素: " + pbq.take().getId());

	}
}
