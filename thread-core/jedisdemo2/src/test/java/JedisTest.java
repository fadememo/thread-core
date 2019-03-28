import redis.clients.jedis.*;

import java.util.List;
import java.util.UUID;

public class JedisTest {
    static final String BUCKET = "BUCKET";
    //自增长的桶,防止多个节点同时请求 导致以时间作为score的重复
    static final String BUCKET_COUNT = "BUCKET_COUNT";
    //放置桶的标示
    static final String BUCKET_MONITOR = "BUCKET_MONITOR";



    //声明一个连接池
    private static JedisPool jedisPool = null;
    private static Jedis jedis= null;
    public static void init(){
        //jedis连接池配置
        JedisPoolConfig config = new JedisPoolConfig();
        //
        config.setMaxIdle(8);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);
        //
        jedisPool = new JedisPool(config,"132.232.40.20",10192,5000,null);


    }



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
        List<Object>results = (List<Object>)transaction.exec();
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
        List<Object> retStrings = transaction.exec();
        //最后一个
        //long rank = (long)retStrings.get(retStrings.size()-1);
        long rank = 0;
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

    public static void close(){
        if(null!=jedis&&jedis.isConnected()){
            //
            jedis.close();
        }
        if(null!=jedisPool&&!jedisPool.isClosed()){
            jedisPool.close();
        }

    }

    /*public static void main(String[] args) throws InterruptedException {
        init();
        final CountDownLatch latch = new CountDownLatch(3);

        jedis = jedisPool.getResource();
        final int limit = 2;
        final long timeout = 1000;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = getTokenFromBucket(jedis, limit, timeout);
                System.out.println("t1.token:  "+ token);
                latch.countDown();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = getTokenFromBucket(jedis, limit, timeout);
                System.out.println("t2.token:  "+ token);
                latch.countDown();
            }
        },"t2");
        *//*Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = getTokenFromBucket(jedis, limit, timeout);
                System.out.println("t3.token:  "+ token);
                latch.countDown();
            }
        },"t3");*//*

        t1.start();
        t2.start();
        //t3.start();
        latch.await();//在这里进行等待,分线程全部完成后
        close();



    }*/

    public void testAssert(){
        int i = 10;
    }
    public void output(){
        String s = "<table border=\"1px\" width=\"400px\" height= \"200px\" align=\"center\" >\n" +
                "\t\t<caption>\"表格标题\" 使用内嵌在table里面的caption标签实现</caption>\n" +
                "\t\t<thead>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th>majoy</th><th colspan=\"2\">majoy+colspan行合并</th>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</thead>\n" +
                "\t\t<tbody>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>never</td><td>never</td><td rowspan = \"2\">never+rowspan列合并</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>die</td><td>die</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</tbody>\n" +
                "\t\t<tfoot>\t\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>666sd</td><td>666sd</td><td>666sd</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</tfoot>\n" +
                "\t</table>";
    }
}
