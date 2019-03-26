package com.bfxy.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void set(String key, String value) {
		ValueOperations<String, String> vops = redisTemplate.opsForValue();
		vops.set(key, value);
	}
	
	public String get(String key) {
		ValueOperations<String, String> vops = redisTemplate.opsForValue();
		return vops.get(key);
	}
	
	public void putHash(String hashKey, String itemKey, String itemValue) {
		HashOperations<String, Object, Object> hops = redisTemplate.opsForHash();
		hops.put(hashKey, itemKey, itemValue);
	}
	
	public String getHash(String hashKey, String itemKey) {
		HashOperations<String, Object, Object> hops = redisTemplate.opsForHash();
		return (String) hops.get(hashKey, itemKey);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
