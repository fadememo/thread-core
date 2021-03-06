package com.cc.springbootredisredpacket;

import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

/**
 * Created by CarlosXiao on 2018/7/3.
 */
@Data
public class RedPacket {

    /**
     * 红包总个数
     */
    private int count;

    /**
     * 红包总额
     */
    private BigDecimal sumMoney;

    /**
     * 剩下个数
     */
    private int surplusCount;

    /**
     * 剩下金额
     */
    private BigDecimal surplusMoney;

    public RedPacket(int count, BigDecimal sumMoney) {
        this.count = count;
        this.sumMoney = sumMoney;
        this.surplusCount = count;
        this.surplusMoney = sumMoney;
    }

    private final Random random = new Random();

    public BigDecimal nextRedPacket() {
        //预设抢到的红包
        BigDecimal money = new BigDecimal(0.01);

        BigDecimal leftAvgMoney = getAvgMoney(this);

        if (surplusCount != 1) {
            while (true) {
                if (leftAvgMoney.floatValue() > 0.01f) {
                    // 剩下单个红包平均值大于0.01 就进行随机分配
                    // 计算浮动值 (剩下金额/剩下人数) * 几人次
                    //d = (剩余金额/剩余个数)*3
                    //
                    BigDecimal d = BigDecimalUtil.multiply(//d = (剩余金额/剩余个数)*3
                            BigDecimalUtil.divide(surplusMoney, new BigDecimal(surplusCount)), //剩余金额/剩余个数
                            new BigDecimal(3));//3

                    // 计算分配金额 随机数 * 浮动范围
                    //money = 随机数*((剩余金额/剩余个数)*3) 保留两位小数
                    money = BigDecimalUtil.multiply(
                            new BigDecimal(random.nextFloat()), d).setScale(2, BigDecimal.ROUND_HALF_UP);

                }

                // break 条件是:剩余红包的平均值 > 当前分配红包 且当前红包 <> 0
                if (leftAvgMoney.floatValue() > money.floatValue() && money.floatValue() != 0) {
                    break;
                }
            }
        } else {
            // 最后一个红包，取两位小数
            money = surplusMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        surplusCount--;
        surplusMoney = BigDecimalUtil.subtract(surplusMoney, money);
        return money;
    }

    private static BigDecimal getAvgMoney(RedPacket redPacket) {
        return redPacket.getSurplusMoney().divide(
                new BigDecimal(redPacket.getSurplusCount()//红包剩余个数
                ),
                MathContext.DECIMAL128)
                .multiply(new BigDecimal(2));
    }


}
