package com.bfxy.thread.core.design.future;

public class RealData implements Data {

	private String result;
	
	public RealData(String queryStr) {
		System.err.println("根据查询参数:" + queryStr + "进行查询数据库操作, 这可能需要5秒左右...");
		try {
			//时间的查询耗时
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = "100条数据";
	}

	@Override
	public String getRequest() {
		return result;
	}

}
