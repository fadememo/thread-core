package club.majoy.springbootmajoycache.mapper;

import club.majoy.springbootmajoycache.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /**
     * 添加entity,并自生成id
     *
     * @return Integer
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(username,password,status) values (#{username},#{password},#{status})")
    public Integer addUser(User user);

    /**
     *
     */
    //@Delete("delete from user where id = (#{id})")
    @Delete("delete from user where id = #{0}")
    public Integer deleteUserById(Integer id);

    /**
     * @param user
     * @return Integer
     */
    @Update("update user set username=#{username}, password=#{password}, status=#{status}")
    public Integer updateUser(User user);

    /**
     *
     */
    @Select(" select id,username,password,status from user where id = #{0} ")
    public User getById(Integer id);

    /**
     *
     */
    @Select("select id,username,password,status from user")
    public List<User> queryUserList();


}