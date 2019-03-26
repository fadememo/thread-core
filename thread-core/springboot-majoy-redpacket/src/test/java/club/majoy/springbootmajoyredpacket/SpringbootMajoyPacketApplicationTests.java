package club.majoy.springbootmajoyredpacket;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMajoyPacketApplicationTests.class)
@Slf4j
public class SpringbootMajoyPacketApplicationTests {

	@Test
	public void contextLoads() {
		RedPacket redPacket = new RedPacket(10, new BigDecimal(100f));
		for (int i = 0; i < redPacket.getCount(); i++) {
			BigDecimal red = redPacket.nextPacket();
			log.info("第{}个红包的金额:{}", i+1, red.toPlainString());
		}
	}

}

