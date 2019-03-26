package club.majoy.springbootmajoylottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 核心用于实现两个功能:
 * 生成获奖号码
 * 返回获奖号码
 */
@SpringBootApplication
public class SpringbootRedisLotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedisLotteryApplication.class, args);
	}

}
