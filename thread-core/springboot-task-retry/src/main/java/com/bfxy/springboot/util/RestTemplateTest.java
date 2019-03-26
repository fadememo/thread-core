package com.bfxy.springboot.util;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

	
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) throws Exception {
		
		/** 
		//get:	
		Map json1 = restTemplate.getForObject("http://localhost:8001/index/get?id=3&name=\"张三\"", Map.class);
		System.err.println("get data: " + json1);
		
		
		//post: 
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1003");
		params.put("name", "张13");
		Map<String, String> json2 = restTemplate.postForObject("http://localhost:8001/index/post", params, Map.class);
		System.out.println("post data: " + json2);
		*/
		
		//exchange
		
		String body = "{\"id\":\"001\", \"name\": \"张三\"}";
		
		HttpHeaders headers = new HttpHeaders();  
		headers.set("token", "bfxy");	
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);

		ResponseEntity<Map> response = restTemplate.exchange(URI.create("http://localhost:8001/index/exchange"),
				HttpMethod.POST, 
				entity, 
				Map.class);
		
		System.err.println("response code: " + response.getStatusCode());
		
		
		
		
		
		
		
		
		
	}
}
