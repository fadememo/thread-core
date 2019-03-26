package club.majoy.springbootmajoyratelimiter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Test.class)
public class methodTest {


    @Test
    public void test(){
        System.out.println("-inf".getBytes());
        long now = System.currentTimeMillis();
        long timeOut = 60l;
        System.out.println(String.valueOf(now - timeOut).getBytes());
        System.out.println();
    }

    public static void main(String[] args) {

    }
}
