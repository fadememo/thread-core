package com.bfxy.springboot.service;

import java.util.concurrent.TimeoutException;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@EnableRetry
@Service
public class RetryService {

	@Retryable(
				value = {RemoteAccessException.class, TimeoutException.class}, //什么异常进行重试
				maxAttempts = 3, 	//重试次数
				backoff = @Backoff( delay = 2000, multiplier =5)
			)
	public void call() throws Exception {
		System.err.println("执行了call方法....");
		//RPC服务调用(timeout...)
		//throw new RemoteAccessException("rpc调用异常...");
		throw new TimeoutException("超时异常..");
	}
	
	@Recover
	public void recover(RemoteAccessException re){
		System.err.println("最终重试失败,进行补偿: " + re.getMessage());
	}
	
	@Recover
	public void recover(TimeoutException te){
		System.err.println("最终重试失败,进行补偿: " + te.getMessage());
	}
	
	
	
	
	
	
	
	
	
	
}
