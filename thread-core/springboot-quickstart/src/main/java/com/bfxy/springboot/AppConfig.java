package com.bfxy.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration			//web.xml -> applicationContext.xml -> mybatis.xml 
@ComponentScan({"com.bfxy.springboot.*"})	//你要进行扫描的包路径
@PropertySource("classpath:my-config.properties")	//加载指定的配置
public class AppConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	@Value("${custom.group}")
	private String customGroup;
	
	@Autowired
	private Environment environment;
	
	@Value("${bfxy.name}")
	private String name;
	
	@Value("${bfxy.title}")
	private String title;
	
	@Value("${bfxy.attr}")
	private String attr;
	
	@Value("${bfxy.dynamic}")
	private String dynamic;
	
	public void output(){
		
		
		System.err.println("通过@Value注解读取配置: " + this.customGroup);
		System.err.println("通过Environment对象读取配置: " + environment.getProperty("custom.team"));
		System.err.println("加载自己的配置文件内容:" + this.name + ", " + this.title + ", " + this.attr);
		LOGGER.info("加载自己的配置文件内容: {}, {}, {}", this.name, this.title, this.attr);
		
		System.err.println("加载自己的配置文件, 动态获取pom里的参数: " + this.dynamic);
	}
	
}
