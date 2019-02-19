package club.majoy.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtilDemo {
    JedisPool jedisPool = new JedisPool();
    Jedis jedis = jedisPool.getResource();


}
