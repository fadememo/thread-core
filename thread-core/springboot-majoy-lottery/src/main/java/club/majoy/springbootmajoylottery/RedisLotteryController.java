package club.majoy.springbootmajoylottery;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("lottery")
public class RedisLotteryController {
    @Resource
    private RedisLotteryService lotteryService;

    /**
     * 生成奖池
     * @param count
     */
    @GetMapping("init/{count}")
    public void init(@PathVariable int count){
        lotteryService.initLottery(count);
    }

    /**
     * 生成抽奖号码
     */
    @GetMapping("get")
    public String getLottery(){
        return lotteryService.getLottery();
    }

}
