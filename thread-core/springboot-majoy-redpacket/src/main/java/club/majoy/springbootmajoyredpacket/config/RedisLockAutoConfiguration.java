package club.majoy.springbootmajoyredpacket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 */
@Configuration
public class RedisLockAutoConfiguration {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(200);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);
        System.out.println("redisHost:"+redisHost);
        System.out.println("redisPort:"+redisPort);
        System.out.println("timeout:"+timeout);
        return new JedisPool(config,redisHost,redisPort,timeout,null);
    }
}
