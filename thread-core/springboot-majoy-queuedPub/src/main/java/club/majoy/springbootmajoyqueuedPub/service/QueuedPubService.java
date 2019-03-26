package club.majoy.springbootmajoyqueuedPub.service;

import club.majoy.springbootmajoyqueuedPub.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Service
@Slf4j
public class QueuedPubService {
    @Resource
    private StringRedisTemplate template;
    private RedisTemplate jacksonTemplate;

    @Value("TakeOut")
    private String redisJob;


    public void publish(String msg){
        template.convertAndSend("teststr",msg );
    }

    public void publish(Job job){
        //序列化;
        ByteArrayOutputStream bot = null;
        ObjectOutputStream oos= null;
        try {
            bot = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bot);
            oos.writeObject(job);
            String objStr = bot.toString("ISO-8859-1");
            template.convertAndSend(redisJob,objStr );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                bot.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
