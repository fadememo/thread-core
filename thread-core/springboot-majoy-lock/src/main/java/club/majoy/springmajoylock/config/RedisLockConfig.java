package club.majoy.springmajoylock.config;

import club.majoy.springmajoylock.aspect.RedisAspect;
import club.majoy.springmajoylock.factory.RedisLockFactory;
import club.majoy.springmajoylock.lock.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Import(RedisAspect.class)
public class RedisLockConfig {

   @Bean
   public RedisLockFactory redisLockFactory(){
       return new RedisLockFactory();
   }

   @Bean
   public RedisKeyGenerator redisKeyGenerator(){
       return new RedisKeyGenerator();
   }



}
