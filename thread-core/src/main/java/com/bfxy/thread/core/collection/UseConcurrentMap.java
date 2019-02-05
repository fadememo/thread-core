package com.bfxy.thread.core.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UseConcurrentMap {

	
	public static void main(String[] args) {
		
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		
		map.put("k1", "v1");
		map.put("k2", "v1");
		map.put("k1", "vv1");
		map.putIfAbsent("k1", "vvv1");
		
		for(Map.Entry<String, Object> me : map.entrySet()){
			System.err.println("key: " + me.getKey() + ", value: " + me.getValue());
		}
		map.size();
	}
		
		
		
		
	
	
	
}
