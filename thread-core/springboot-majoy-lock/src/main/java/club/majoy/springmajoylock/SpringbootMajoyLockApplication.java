package club.majoy.springmajoylock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分布式锁实现逻辑;基于redis的setnx指令,当key存在时,不进行操作,不存在key则新增
 * 所以:
 * 给所要修改的数据id作为key,修改时先去setnx redis数据,如果set成功进行后续修改,修改后删除gaikey,否则不执行操作.
 * 为防止死锁,给key设置超时时间
 *
 */
@SpringBootApplication
public class SpringbootMajoyLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMajoyLockApplication.class, args);
	}

}
