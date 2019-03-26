package jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis java api
 */
public class JedisUtilDemo {
    //声明一个连接池实例,应该在使用时去实例化
    private static JedisPool jedisPool = null;
    private static Jedis jedis = null;
    public static void init(){
        //Jedis连接池配置
        JedisPoolConfig jedisCfg = new JedisPoolConfig();
        //最大的空闲连接数
        jedisCfg.setMaxIdle(8);
        //最大的连接数
        jedisCfg.setMaxTotal(100);
        //最大等待连接时间,超时还没成功就丢异常
        jedisCfg.setMaxWaitMillis(1000);
        //设置在borrow连接的时候是否校验
        jedisCfg.setTestOnBorrow(false);
        //final GenericObjectPoolConfig poolConfig, final String host, int port,int timeout, final String password
        //这里写入操作只能连接master进行
        jedisPool = new JedisPool(jedisCfg,"132.232.40.20",10192,5000,null);
        //在这里初始化不好是因为 不能完成闭环的开启和关闭
        //jedis = jedisPool.getResource();
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
    /**
     * redis数据类型(6-1)String
     * 二进制的String可以存放序列化对象,图片,字符串,数值,string 类型的值最大能存储 512MB
     *
     */
    public static void testString(){
        jedis = jedisPool.getResource();
        // keys *
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        // set(key,value)
        jedis.set("key3", "value3");
        // append (key , str)
        jedis.append("key3", "appendstr");
        System.out.println(jedis.get("key3"));
        //getset
        jedis.getSet("key3", "new key3");
        System.out.println(jedis.get("key3"));
        //监视 key1和key2 的值
        jedis.watch("key1","key2");
        //开启事务
        Transaction multi = jedis.multi();
        System.out.println(multi.getSet("key3", "value34"));
        System.out.println(multi.incr("foo"));
        List<Object> execStr = multi.exec();
        System.out.println(jedis.getSet("key3", "value3"));//value3
        System.out.println(jedis.incr("foo"));//12
        System.out.println(execStr);//12
        jedis.del("key3");
        //关闭事务
        //jedis.close();
        //沙雕网友这里居然是 关闭redis服务的命令
        //jedis.shutdown();
    }
    /**
     * redis数据类型(6-2)Hash
     * Hash 是String类型的field和value的映射表,适合存储对象,每个 hash 可以存储 232 -1 键值对（40多亿）。
     */
    public static void testHash(){
        jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        //hmset
        jedis.hmset("hmap", new HashMap<String,String>(){{
            put("hkey1", "hvalue1");
            put("hkey2", "hvalue2");
            put("hkey3", "hvalue3");
            }});
        //hget
        System.out.println(jedis.hget("hmap", "hkey1"));
        //hget
        Map<String, String> hmap = jedis.hgetAll("hmap");
        System.out.println(hmap);
        //hkeys
        Set<String> hkeys = jedis.hkeys("hmap");
        //hvals
        List<String> hvals = jedis.hvals("hmap");
        //hexists
        Boolean hexists = jedis.hexists("hmap", "hkeys");
        System.out.println("hexists:"+hexists);
        //hlen
        Long hlem = jedis.hlen("hmap");
        System.out.println("hlem:"+hlem);
        jedis.hsetnx("hmap", "hkey3", "anotherHvalue");
        System.out.println(hkeys);
        System.out.println(hvals);
        //jedis.close();
    }
    /**
     * redis数据类型(6-3)set
     * Set 是String类型的无序集合,通过intset 或者 hashtable实现,可以取交集 并集和差集,是string类型的无序集合。
     * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
     */
    public static void testSet(){
        //注意因为set的无序性,很多操作都是随机的
        jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        //sadd  插入后放置元素的顺序有点奇怪,还是输出的时候奇怪?
        jedis.sadd("set1","member1","member2","member3","member14","member15","member16","member7","member8");
        jedis.sadd("set2","member1","member22","member23","member24","member25","member26","member7","member8");
        jedis.sadd("set3","member1","member2","member3","member14","member15","member36","member37","member38");
        //srand
        String member = jedis.srandmember("set1");
        System.out.println("member:"+member);
        //smember
        Set<String> set1 = jedis.smembers("set1");
        System.out.println("set1:"+set1);
        //sinter 只对 set 和 set3 做了交集????是对全部集合做了交集
        Set<String> sinter = jedis.sinter("set1", "set3", "set2");
        System.out.println("sinter:"+sinter);
        //sunion
        Set<String> sunion = jedis.sunion("set1", "set2", "set3");
        System.out.println("sunion:"+sunion);
        //sort              //排序只对zset有用
        //List<String> set3 = jedis.sort("set3");
        //System.out.println("set3:"+set3);
        //sdiff 是球了连续两次差集
        Set<String> sdiff = jedis.sdiff("set1", "set3", "set2");
        System.out.println("sdiff:"+sdiff);
        //spop
        //Set<String> setpop = jedis.spop("set1", 3);
        //System.out.println("setpop:"+setpop);
        System.out.println("scard:"+jedis.scard("set1"));
        //jedis.close();
    }
    /**
     * redis数据类型(6-4)
     * Zset 有序集合内部两种编码方式,ziplist和skiplist,不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
     * zset的成员是唯一的,但分数(score)却可以重复,复杂度当进行跳跃列表的时候复杂度应该是 logn,
     */
    public static void testZset(){
        jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        //
        jedis.zadd("zset1",new HashMap<String,Double>(){{
            put("zmember1",new Double(1));
            put("zmember2",new Double(2));
            put("zmember3",new Double(3));
            put("zmember4",new Double(4));
            put("zmember5",new Double(5));
            put("zmember6",new Double(6));
            put("zmember7",new Double(7));
        }});
        jedis.zadd("zset2",new HashMap<String,Double>(){{
            put("zmember1",new Double(1));
            put("zmember2",new Double(2));
            put("zmember3",new Double(3));
            put("zmember24",new Double(4));
            put("zmember25",new Double(5));
            put("zmember26",new Double(6));
            put("zmember27",new Double(7));
        }});
        //spop没有java没看到有实现该功能
        Long zset1 = jedis.zremrangeByRank("zset1", 3,5);
        System.out.println("zset1:"+zset1);
        //zcard 计数
        Long zcard = jedis.zcard("zset1");
        System.out.println("zset1:"+zset1);
        //zcount
        Long zcount = jedis.zcount("zset", 2, 5);
        System.out.println("zcount:"+zcount);
        //zincrby
        Double zincrby = jedis.zincrby("zset1", 3, "zmember2");
        System.out.println("zincrby:"+zincrby);
        //zinterstore
        Long zinterstore = jedis.zinterstore("zset1","zset2");
        System.out.println("zinterstore:"+zinterstore);
        //zrange
        Set<String> zset1Range = jedis.zrange("zset1", 0, -1);
        System.out.println("zset1Range:"+zset1Range);
        //zrank
        Long zrank = jedis.zrank("zset1", "zmember2");
        System.out.println("zrank:"+zrank);
        //zrangebyscore
        Set<String> zrangeByScore = jedis.zrangeByScore("zset1", 2, 7);
        System.out.println("zrangeByScore"+zrangeByScore);
        //jedis.close();
    }
    /**
     * redis数据类型(6-6)List
     * List 是一个链表结构的集合,主要功能有push和pop获取元素等,双向链表结构,列表最多可存储 232 - 1 元素 (4294967295, 每个列表可存储40多亿)
     */
    public static void testList(){
        jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        jedis.lpush("llist1","item9","item8","item7","item6","item5","item4","item3","item2","item1");
        jedis.rpush("llist2","item1","item2","item3","item4","item5","item6","item7","item8","item9");
        List<String> llist1 = jedis.lrange("llist1", 0, -1);
        System.out.println("llist1:"+ llist1);
        String lset = jedis.lset("llist2", 3 - 1, "item33");
        System.out.println("lset:"+ lset);
        jedis.lpushx("list1","item8");
        //System.out.println("lpushx:"+ lpushx);
        String llist11 = jedis.ltrim("llist1", 1, 100);
        System.out.println("llist11:"+ llist11);
        //jedis.lset(, , )
        //jedis.close();
    }

    public static void main(String[] args) {
        init();

        try {
            //testString();
            //testHash();
            //testSet();
            testZset();
            //testList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

}
