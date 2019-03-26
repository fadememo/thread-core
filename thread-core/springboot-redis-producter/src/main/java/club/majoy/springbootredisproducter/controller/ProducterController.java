package club.majoy.springbootredisproducter.controller;

import club.majoy.springbootredisproducter.service.ProducterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class ProducterController {
    @Resource
    private ProducterService producterService ;

    @GetMapping("/product/{msg}")
    @PostMapping("/product/alloha/{msg}")
    public String product(@PathVariable String msg){
        //发送消息
        producterService.product(msg);
        return msg;
    }

}

