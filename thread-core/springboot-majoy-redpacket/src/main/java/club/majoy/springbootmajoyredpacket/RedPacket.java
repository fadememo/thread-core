package club.majoy.springbootmajoyredpacket;

import club.majoy.springbootmajoyredpacket.util.BigDecimalUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

/**
 * 采用领取时生成金额做法,因此属性有 总额,个数,剩余个数,当前最大金额
 */
@Data
public class RedPacket {
    private int count;//数目
    private BigDecimal sumMoney;//总金额
    private int surplusCount;//剩余个数
    private BigDecimal surplusMoney;//剩余金额
    private BigDecimal maxMoney;//当前最大金额

    /**
     * 初始化一个未被分配的红包
     * @param count
     * @param sumMoney
     */
    public RedPacket(int count,BigDecimal sumMoney){
        this.count = count;
        this.sumMoney = sumMoney;
        this.surplusCount = count;
        this.surplusMoney = sumMoney;
        this.maxMoney = new BigDecimal(0f,MathContext.DECIMAL128) ;
    }

    private final Random random = new Random();

    public BigDecimal nextPacket() {
        //预设青岛的红包
        BigDecimal money = new BigDecimal(0.01);
        //
        BigDecimal leftAvgMoney = getAvgMoney(this);
        if(surplusCount != 1){
            while(true){
                //如果大于1分钱继续进行分红包
                if(leftAvgMoney.floatValue() > 0.01f){
                    //为什么 平均数要*3 ,预设有三个人
                    BigDecimal d = BigDecimalUtil.multiply(
                            BigDecimalUtil.divide(surplusMoney, new BigDecimal(surplusCount)),
                            new BigDecimal(3)
                    );

                    // 计算分配金额 随机数 * 浮动范围
                    money = BigDecimalUtil.multiply(new BigDecimal(random.nextFloat()), d).setScale(2, BigDecimal.ROUND_HALF_UP);

                }
                //否则
                // break
                if (leftAvgMoney.floatValue() > money.floatValue() && money.floatValue() != 0) {
                    break;
                }
            }
        }else{
            //这里或许应该写成surplus,可能会超出
            money = surplusMoney.setScale(2, BigDecimal.ROUND_UP);
        }
        surplusCount --;
        surplusMoney = BigDecimalUtil.subtract(surplusMoney,money );
        return money;
    }

    /**
     * 剩余金额/剩余个数 获得平均红包金额
     * @param redPacket
     * @return
     */
    private static BigDecimal getAvgMoney(RedPacket redPacket) {
        return redPacket.getSurplusMoney().divide(
                new BigDecimal(redPacket.getSurplusCount()),
                MathContext.DECIMAL128)
                .multiply(new BigDecimal(2));
    }
}
