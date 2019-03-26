package club.majoy.springbootmajoyratelimiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")//从配置文件获取,配置文件是application
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public JedisPool jedisPool(){
        //
        JedisPoolConfig config = new JedisPoolConfig();
        //空闲连接数?
        config.setMaxIdle(10);
        //最大连接数
        config.setMaxTotal(200);
        //连接空闲保持时间
        config.setMaxWaitMillis(1000);
        //轮询检测连接有效
        config.setTestOnBorrow(false);

        return new JedisPool(config,redisHost,redisPort,5000);
    }

}
