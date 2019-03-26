package club.majoy.springmajoylock.controller;

import club.majoy.springmajoylock.service.LockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;


@RestController
public class LockController {

    @Resource
    private LockService lockService ;


    @RequestMapping("lock")
    private String lock(){
        String id = UUID.randomUUID().toString();
        lockService.test(id);
        return new String(id);
    }
}
