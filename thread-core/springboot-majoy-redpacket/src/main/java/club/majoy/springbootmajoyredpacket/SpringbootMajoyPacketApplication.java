package club.majoy.springbootmajoyredpacket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主要用来给大量用户生成红包序列,是初始化时就完成红包划分,然后每次请求就返回一个金额
 * 使用了数据类型list,循环存入金额.key 使用的是sessionId,我觉得应该使用sessionid_时间戳会好一些
 */
@SpringBootApplication
public class SpringbootMajoyPacketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMajoyPacketApplication.class, args);
	}

}
