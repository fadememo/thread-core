package club.majoy.springbootmajoycache.service;

import club.majoy.springbootmajoycache.entity.User;
import club.majoy.springbootmajoycache.mapper.UserMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 保存
     *
     * @return 即生成的key为user_1形式
     */
    //@CachePut(value="usercache", key = "'user_' + #user.id.toString()", unless = "#result eq null")

    /**
     * 缓存相关的注解:
     * value 是缓存的名称,定义在配置文件中.
     * key 可以为空,spel表达式或者缺省按照方法参数进行组合
     * condition 缓存条件,使用spel表达式,true 时候进行缓存
     * cacheable能够根据方法的请求参数对齐结果进行缓存
     * cacheput 根据请求参数对结果进行缓存,会触发真实方法调用
     * cacheevict 针对方法[配置,可根据条件清空缓存
     * #result 方法执行返回值
     *
     * @param user
     * @return
     */
    @CachePut(value="usercache", key = "'user_' + #user.id.toString()", unless = "#result eq null")
    public User save(User user) {
        if(null!=user.getId()){
            userMapper.updateUser(user);
        }else{
            userMapper.addUser(user);
        }
        return user;
    }

    /**
     *
     */
    @Cacheable(value="usercache",key="'user_'+#id",unless = "#result eq null")
    public User findUser(Integer id) {
        return userMapper.getById(id);
    }

    /**
     * 删除同时删除缓存
     */
    @CacheEvict(value="usercache",key="'user_'+#id",condition = "#result eq true")
    public boolean delUser(Integer id) {
        return userMapper.deleteUserById(id)>0;
    }

}
