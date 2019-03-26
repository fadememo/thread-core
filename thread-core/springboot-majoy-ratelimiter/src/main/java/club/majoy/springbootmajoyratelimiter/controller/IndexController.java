package club.majoy.springbootmajoyratelimiter.controller;


import club.majoy.springbootmajoyratelimiter.RedisRateLimter;
import club.majoy.springbootmajoyratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private JedisPool redisPool;
    @GetMapping("/t1")
    @RateLimiter(limit=2,timeout = 1000)
    public void t1(){
        //
        /*String token = RedisRateLimter.getTokenFromBucket(redisPool.getResource() , 3, 10000);
        if(null==token){
            throw new RuntimeException("t1未能获取到令牌");
        }*/
    }
    @GetMapping("/t2")
    @RateLimiter(limit=3,timeout = 1000)
    public void t2(){
        /*String token = RedisRateLimter.getTokenFromBucket(redisPool.getResource() , 10, 10000);
        if(null==token){
            throw new RuntimeException("t2未能获取到令牌");
        }*/
    }
    @GetMapping("/t3")
    @RateLimiter(limit=3,timeout = 1000)
    public void t3(){
        /*String token = RedisRateLimter.getTokenFromBucket(redisPool.getResource() , 10, 10000);
        if(null==token){
            throw new RuntimeException("t3未能获取到令牌");
        }*/
    }



}
