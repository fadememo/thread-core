package club.majoy.springbootmajoycache;

import club.majoy.springbootmajoycache.entity.User;
import club.majoy.springbootmajoycache.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =SpringbootMajoyCacheApplication.class)
@Slf4j
public class SpringbootMajoyCacheApplicationTests {

	@Resource
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAdd(){
		User user = new User("majoy","neverlove",1);
		userMapper.addUser(user);
		Assert.assertNotNull(user.getId());
	}
	@Test
	public void testList(){
		List<User>userList = userMapper.queryUserList();
		log.info("userList:{} ",userList.size());
	}

}
