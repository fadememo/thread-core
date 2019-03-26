package club.majoy.springmajoylock.factory;

import club.majoy.springmajoylock.lock.RedisLockInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.UUID;

public class RedisLockFactory {

    private RedisTemplate redisTemplate;

    //Jedis在集群环境下不支持lua脚本,因此
    // lettuce 工具来连接cluster
    private static final String LUA_SCRIPT_LOCK = "return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])";
    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<String>(LUA_SCRIPT_LOCK, String.class);
    private static final String LUA_SCRIPT_UNLOCK = "if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])) else return '0' end";
    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<String>(LUA_SCRIPT_UNLOCK, String.class);

    public RedisLockInfo tryLock(String redisKey,long expire,long tryTimeout){
        //判断,不符合就抛出异常 illegleArgument
        Assert.isTrue(tryTimeout>0, "tryTimeout必须大于0");
        //时间戳
        long timeStamp = System.currentTimeMillis();
        //尝试获得锁的次数
        int tryCount = 0;
        //锁id,也可以使用zookeeper 等
        String lockId = UUID.randomUUID().toString();
        //判断 如果在超时时间范围之内,循环进行是否加锁 判断
        while((System.currentTimeMillis()-timeStamp)<tryTimeout){
            try{
                //执行lua 脚本 setnx命令,新增key 并设置超时时间
               Object lockResult = redisTemplate.execute(SCRIPT_LOCK,
                       redisTemplate.getStringSerializer(),
                       redisTemplate.getStringSerializer(),
                       Collections.singletonList(redisKey),
                       lockId,
                       String.valueOf(expire));
               tryCount++;
               //如果前面setnx执行成功,返回锁
               if(null != lockResult && "OK".equals(lockResult)){
                   return new RedisLockInfo(lockId,redisKey,expire,tryTimeout,tryCount);
               }else{
                   //否则睡眠之后再问
                   Thread.sleep(50);
               }
            }catch(Exception e){
                e.printStackTrace();
            }finally{

            }
        }
        return null;
    }

    /**
     *  lua 脚本可以 保证加锁和解锁的 原子性
     * @param redisLockInfo
     * @return
     */
    public boolean releaseLock(RedisLockInfo redisLockInfo){
        Object releaseResult = null;
        try{
            //释放锁,
            //lua脚本:,如果get key成功,则删除key
            releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
                    redisTemplate.getStringSerializer(),
                    redisTemplate.getStringSerializer(),
                    Collections.singletonList(redisLockInfo.getRedisKey()),
                    redisLockInfo.getLockId());
        }catch(Exception e){
            e.printStackTrace();
        }
        //
        return null!= releaseResult && releaseResult.equals(1);



    }


}
