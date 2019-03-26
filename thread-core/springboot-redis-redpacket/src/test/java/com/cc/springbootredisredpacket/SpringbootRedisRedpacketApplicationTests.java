package com.cc.springbootredisredpacket;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRedisRedPacketApplication.class)
@Slf4j
public class SpringbootRedisRedpacketApplicationTests {

	@Test
	public void contextLoads() {
		RedPacket redPacket = new RedPacket(10, new BigDecimal(100f));
		for (int i = 0; i < redPacket.getCount(); i++) {
			BigDecimal red = redPacket.nextRedPacket();
			log.info("第{}个红包的金额:{}", i+1, red.toPlainString());
		}
	}
	@Test
	public void getAvgMoney() {
		RedPacket redPacket = new RedPacket(100,new BigDecimal(20));
		BigDecimal cnt = new BigDecimal(redPacket.getSurplusCount()) ;
		BigDecimal surplusMoney = redPacket.getSurplusMoney() ;
		int surplusCount = redPacket.getSurplusCount();
		for(int i = 0;i<cnt.intValue();i++){
			BigDecimal avg =  surplusMoney.divide(cnt,MathContext.DECIMAL128);
			BigDecimal avg2 =  avg.multiply(new BigDecimal(2));
			//log.info("avg = {}", avg.toPlainString());
			log.info("["+(i+1)+"]"+",avg2 = {}", avg2.toPlainString());

			BigDecimal money = new BigDecimal(0.01);
			Random random = new Random();
			BigDecimal leftAvgMoney = avg2;
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
			log.info("["+(i+1)+"]"+",Money = {}", money.toPlainString());
			log.info("["+(i+1)+"]"+",surplusMoney = {}", surplusMoney.toPlainString());
		}

	}

}
