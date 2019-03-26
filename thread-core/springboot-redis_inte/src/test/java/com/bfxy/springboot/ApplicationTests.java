package com.bfxy.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bfxy.springboot.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
	private RedisService redisService;
	
	@Test
	public void test() throws Exception {
//		redisService.set("51cto", "12345");
//		redisService.set("bhx", "67890");
//		
//		System.err.println(redisService.get("51cto"));
//		System.err.println(redisService.get("bhx"));
		
//		redisService.putHash("user:0001", "name", "z3");
//		redisService.putHash("user:0001", "age", "20");
		
		System.err.println(redisService.getHash("user:0001", "age"));
	}
	

}
