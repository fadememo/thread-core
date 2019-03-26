package club.majoy.springbootredisconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Configuration
public class ConsumerConfig {

    //通过@value注解注入application.properties中的值
    @Value("${redis.msg.topic}")
    private String msgTopic;

    //消息监听容器
    @Bean
    public MessageListenerAdapter adapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }
    //不需要这个
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * 返回一个消息队列的容器
     * @param connectionFactory
     * @param adapter
     * @return
     */
    //消息容器
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter adapter){

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(adapter, new PatternTopic(msgTopic));

        return container;
    }

    /**
     *
     */
    //
    @Bean
    public Receiver receiver(CountDownLatch latch){
        return new Receiver(latch) ;
    }

    /**
     *
     * @return
     */
    @Bean
    public CountDownLatch latch(){
        return new CountDownLatch(1);
    }

}
