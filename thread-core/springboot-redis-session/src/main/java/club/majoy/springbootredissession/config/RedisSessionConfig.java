package club.majoy.springbootredissession.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/***
 * 这个程序
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

}
