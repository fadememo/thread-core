package club.majoy.springbootmajoyqueuedSub.config;

import club.majoy.springbootmajoyqueuedSub.receiver.MerchantReceiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Configuration
public class QueuedSubConfig {

    @Value("TakeOut")
    private String redisJob;

    @Bean
    public RedisMessageListenerContainer container(
            RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter
    ){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisJob));
        return container;
    }
    @Bean
    public MessageListenerAdapter listenerAdapter(MerchantReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMsg");
    }
    @Bean
    public MerchantReceiver receiver(CountDownLatch latch){
        return new MerchantReceiver(latch);
    }

    @Bean
    public CountDownLatch latch(){
        return new CountDownLatch(1);
    }



}
