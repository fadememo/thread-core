package com.bfxy.thread.core.design.future;

public class FutureClient {

	public Data request(final String queryStr) {
		
		FutureData futureData = new FutureData();
		
		//异步的起一个线程去进行相应的处理
		new Thread(new Runnable() {
			@Override
			public void run() {
				//需要把请求参数 设置到真实的数据处理对象中去
				RealData realData = new RealData(queryStr);
				//真实请求处理完成以后, 我们要进行设置结果给包装类对象
				futureData.setRealData(realData);
			}
		}).start();
		
		
		return futureData;
		
	}
	
	
}
