package club.majoy.springmajoylock.aspect;

import club.majoy.springmajoylock.factory.RedisLockFactory;
import club.majoy.springmajoylock.lock.RedisKeyGenerator;
import club.majoy.springmajoylock.lock.RedisLock;
import club.majoy.springmajoylock.lock.RedisLockInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这个切面的意义 在于?
 * 在
 */
@Aspect
@Component
@Slf4j
public class RedisAspect {

    @Autowired
    private RedisLockFactory redisLockFactory;

    @Autowired
    private RedisKeyGenerator redisKeyGenerator = null;

    @Around("@annotation(redisLock)")//切点在LockService 的方法上
    public Object around(ProceedingJoinPoint point,RedisLock redisLock) throws Throwable{
        RedisLockInfo redisLockInfo = null;
        try{
            String keyName = redisKeyGenerator.getKeyName(point,redisLock);
            if(null != redisLockInfo) {
                Object result = point.proceed();
                return result;
            }
        }catch(Throwable e){
            log.error("around exception",e);
            throw e;
        }finally{
            if(null != redisLockInfo){
                redisLockFactory.releaseLock(redisLockInfo);
            }
        }
        return null;
    }



}
