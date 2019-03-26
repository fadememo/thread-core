package club.majoy.springbootredisconsumer.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    //定义计数器
    private CountDownLatch latch ;
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    public Receiver(CountDownLatch latch){
        this.latch = latch;
    }

    public void receiveMessage(String msg){
        logger.info("receive msg : {}",msg);
    }
}
