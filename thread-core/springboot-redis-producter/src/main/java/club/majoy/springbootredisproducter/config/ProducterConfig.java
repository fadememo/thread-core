package club.majoy.springbootredisproducter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ProducterConfig {
    @Bean
    public StringRedisTemplate template(RedisConnectionFactory configurationFactory){
        return new StringRedisTemplate(configurationFactory);
    }
}
