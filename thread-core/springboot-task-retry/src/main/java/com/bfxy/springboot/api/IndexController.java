package com.bfxy.springboot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bfxy.springboot.entity.User;

@RestController
public class IndexController {
		
	private static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);  
	
	@RequestMapping(value = "/get", produces={"application/json;charset=UTF-8"}, method={RequestMethod.GET})
	public User getJson(User user){
		System.err.println("---------===== get ====---------");
		LOGGER.info("id : " + user.getId() + ", name : " + user.getName());
		return user;
	}
	
	@RequestMapping(value = "/post", produces={"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"}, method={RequestMethod.POST})
	public User postJson(@RequestBody User user){
		System.err.println("---------===== post ====---------");
		LOGGER.info("id : " + user.getId() + ", name : " + user.getName());
		return user;
	}
	
	
	@RequestMapping(value = "/exchange", produces={"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"}, method={RequestMethod.POST})
	public void exchange(@RequestHeader("token")String token, @RequestBody User user){
		System.err.println("exchange 4 post token: " + token + ", user: " + user.getName());
	}	
	
	
	
	
	
	
	
	
	
}
