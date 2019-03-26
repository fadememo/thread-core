package club.majoy.springbootmajoycache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 现在梳理一下这个程序的开发思路:
 * 使用redis做数据缓存,那么数据就同时存在于两个地方,数据库和redis数据库
 * 1,查询数据的的时候,先去redis查询,查不到查询数据库.查询出结果后,结果保存到redis.
 * 2,数据保存时,同时保存到redis数据库
 * 3,数据删除时,同时
 * 4,数据修改时,同时修改到redis
 * 5,参考service 配置 //@CachePut(value="usercache", key = "'user_' + #user.id.toString()", unless = "#result eq null")
 *
 *
 *
 */
@SpringBootApplication
@MapperScan("club.majoy.springbootmajoycache.mapper")
public class SpringbootMajoyCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMajoyCacheApplication.class, args);
	}

}
