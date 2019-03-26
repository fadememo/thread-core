package club.majoy.springbootmajoylottery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisLotteryApplicationTests {

	@Test
	public void contextLoads() {
		int count = 100;
		ArrayList lotteryList = new ArrayList();
		for(int i = 1;i<=count;i++){
			lotteryList.add(String.format("%0"+String.valueOf(count).length()+"d",i));
		}
		System.out.println(lotteryList);
	}

}
