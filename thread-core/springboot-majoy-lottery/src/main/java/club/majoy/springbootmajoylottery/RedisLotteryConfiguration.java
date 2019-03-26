package club.majoy.springbootmajoylottery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisLotteryConfiguration  {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public JedisPool jedisPool() {
        // jedis 连接池的配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(2000);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);
        return new JedisPool(config, redisHost, redisPort, 5000);
    }


}
