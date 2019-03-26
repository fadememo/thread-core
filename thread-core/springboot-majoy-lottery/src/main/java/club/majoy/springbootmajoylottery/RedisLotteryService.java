package club.majoy.springbootmajoylottery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RedisLotteryService {
    //
    static final String LOTTERY_POOL = "LOTTERY_POOL";
    @Resource
    private JedisPool jedisPool;

    static int count;
    /**
     * 按照容量生成list 奖池
     * @param count
     */
    public  void initLottery(int count){
        Jedis jedis = jedisPool.getResource();
        try {
            List<String>lotteryList = new ArrayList<>();
            for(int i = 1;i<=count;i++){
                lotteryList.add(String.format("%0"+String.valueOf(count).length()+"d",i));
            }
            //随机打乱顺序
            Collections.shuffle(lotteryList);
            //
            Pipeline pl = jedis.pipelined();
            for(int i = 0;i<lotteryList.size() ;i++){
                //jedis.lpush(LOTTERY_POOL,lotteryList.get(i));
                pl.lpush(LOTTERY_POOL,lotteryList.get(i) );
            }
            pl.sync();
            //鬼鸡儿慢
            /*for(int i = 0;i<lotteryList.size() ;i++){
                jedis.lpush(LOTTERY_POOL,lotteryList.get(i));
            }*/
        } finally {
            jedis.close();
        }
    }

    /**
     * 因为list 已经存放到 redis中,所以可以直接取任意一位即可完成抽奖的随机性
     */
    public String getLottery(){

        String num;
        long left=0;
        Jedis jedis = jedisPool.getResource();
        count ++;
        try {
            num = jedis.lpop(LOTTERY_POOL);
            left = jedis.llen(LOTTERY_POOL);
            log.info("当前取出的号码是"+num+" ,剩余号码有 "+left + " ,当前计数 "+count);
            if(StringUtils.isEmpty(num)){
                throw new RuntimeException("sold out");
            }
        } finally {
            jedis.close();
            //  jedis.disconnect();

        }
        return num;
    }



}
