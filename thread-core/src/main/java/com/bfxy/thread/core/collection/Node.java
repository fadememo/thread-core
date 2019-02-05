package com.bfxy.thread.core.collection;

public class Node implements Comparable<Node>{

	private int id;
	private String name;
	
	public Node() {
	}
	
	public Node(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Node node) {
		return this.id > node.id ? 1 : (this.id < node.id ? -1 : 0);
	}
	
	public String toString() {
		return this.id  + ":" + this.name;
	}

}
