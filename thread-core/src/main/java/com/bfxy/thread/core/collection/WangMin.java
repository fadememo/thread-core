package com.bfxy.thread.core.collection;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class WangMin implements Delayed {

	private String id;
	
	private String name;
	
	private long endTime;	//上网的截止时间
	
	private final TimeUnit timeUnit = TimeUnit.SECONDS;
	
	public WangMin() {
	}

	public WangMin(String id, String name, long endTime) {
		this.id = id;
		this.name = name;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 用来判断是否到了下机时间
	@Override
	public long getDelay(TimeUnit unit) {
		return endTime - System.currentTimeMillis();
	}
	
	@Override
	public int compareTo(Delayed delayed) {
		WangMin w = (WangMin)delayed;
		return this.getDelay(timeUnit) - w.getDelay(timeUnit) > 0 ? 1 : -1;
	}

}
