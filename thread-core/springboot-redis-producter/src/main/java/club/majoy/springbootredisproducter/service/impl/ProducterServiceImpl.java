package club.majoy.springbootredisproducter.service.impl;

import club.majoy.springbootredisproducter.service.ProducterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProducterServiceImpl implements ProducterService {

    static Logger logger = LoggerFactory.getLogger(ProducterServiceImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate  ;

    @Value("${redis.msg.topic}")
    private String redisTopic;

    @Override
    public void product(String msg) {
        stringRedisTemplate.convertAndSend(redisTopic, msg);
        logger.info("productor send msg: {}", msg);
    }
}
