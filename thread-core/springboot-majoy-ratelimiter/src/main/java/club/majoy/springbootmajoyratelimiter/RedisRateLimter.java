package club.majoy.springbootmajoyratelimiter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.List;
import java.util.UUID;

/**
 *
 *
 *
 *
 *
 */
public class RedisRateLimter {
    //单桶会导致并发排序数相同
    static final String BUCKET = "BUCKET";
    //自增长的桶,防止多个节点同时请求 导致以时间作为score的重复
    static final String BUCKET_COUNT = "BUCKET_COUNT";
    //放置桶的标示
    static final String BUCKET_MONITOR = "BUCKET_MONITOR";

    /**
     * limit 令牌桶容量
     * @param jedis
     * @param limit
     * @param timeout
     * @return
     */
    //从redis令牌桶获取一个令牌
    public static String getTokenFromBucket(Jedis jedis, int limit, long timeout) {
        /**
         * 1,清除过期的信号量令牌,则我们需要一个请求的标示.
         * 2,将新的信号量放进来
         * 3,获取一个信号量的排名
         * 4,判断信号量排名,如果信号量不在限定的排名里面就移除这个信号量
         *
         *
         *
         */
        //
        //请求标示
        String identfierString = UUID.randomUUID().toString();
        //当前系统时间
        long now = System.currentTimeMillis();
        //通过事务
        Transaction transaction = jedis.multi();
        //1,清除超出排名的信号量
        transaction.zremrangeByScore(BUCKET_MONITOR.getBytes(), "-inf".getBytes(), String.valueOf(now - timeout).getBytes());
        //加一个权重参数指定两个有序集合求交集之后的分数权重
        ZParams params = new ZParams();
        params.weightsByDouble(1.0,1.0);
        //取交集,目的是
        transaction.zinterstore(BUCKET, params,BUCKET_MONITOR);
        //自增计数器,
        transaction.incr(BUCKET_COUNT);
        List<Object>results = transaction.exec();
        long counter = (Long)results.get(results.size()-1);
        //开启新的事务
        transaction = jedis.multi();
        //transaction.zremrangeByScore(zset[key], begin,end )
        //2,插入新增信号量 zadd(zset[key],score,member)
        transaction.zadd(BUCKET, counter, identfierString);
        transaction.zadd(BUCKET_MONITOR, now, identfierString);
        //获得排名
        transaction.zrank(BUCKET,identfierString);
        //3,获取信号量的排名,首先需要执行完毕才能看到排名,返回值是事务的执行结果列表
        results = transaction.exec();
        //最后一个
        long rank = (long)results.get(results.size()-1);
        //4,判断信号量排名,如果不在,则移除
        if(rank<limit){
            return identfierString;
        }else{
            //没有获取到信号量,则删除数据
            transaction = jedis.multi();
            transaction.zrem(BUCKET,identfierString);
            transaction.zrem(BUCKET_MONITOR,identfierString);
        }
        return null;
    }


}
