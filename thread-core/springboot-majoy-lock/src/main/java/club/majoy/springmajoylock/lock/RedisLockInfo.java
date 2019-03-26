package club.majoy.springmajoylock.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //该注解用途是:
@AllArgsConstructor   //该注解用途是:
@NoArgsConstructor   //该注解用途是:
public class RedisLockInfo {
    /**
     * 锁ID UUID
     */
    private String lockId;
    /**
     * REDIS KEY
     */
    private String redisKey;
    /**
     * 过期时间,过期自动删除
     */
    private Long expire;
    /**
     * 尝试获取锁超时时间
     */
    private Long tryTimeout;
    /**
     * 尝试获取锁次数
     */
    private int tryCount;


}
