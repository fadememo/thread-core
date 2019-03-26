package club.majoy.springmajoylock.service;

import club.majoy.springmajoylock.lock.RedisLock;
import org.springframework.stereotype.Service;

@Service
public class LockService {

    @RedisLock(keys="#id",expire = 30000,tryTimeout = 1000)
    public String test(String id){
        return id;
    }
}
