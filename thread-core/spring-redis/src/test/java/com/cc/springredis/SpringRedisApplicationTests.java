package com.cc.springredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRedisApplication.class)
public class SpringRedisApplicationTests {

	@Resource
	private RedisUtil redisUtil;

	@Test
	public void testRedis() {
		redisUtil.set("test", "1111111111");
		System.out.println(redisUtil.get("test"));
	}

}
