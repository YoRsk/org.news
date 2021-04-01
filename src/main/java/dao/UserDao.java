package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangxin
 * @time 2018/12/24  14:30
 */
public interface UserDao {
    /*
    * 插入用户
    * */
     int insertUser(User user);

    /*
    * 根据昵称和密码查询用户验证登录
    * */
    User queryById(@Param("username") String username, @Param("userPassword") String userPassword);

    /*
    * 根据名称查找用户
    * */
    User queryByName(@Param("username") String username);

    /*
     * 根据id查找用户
     * */
    User queryByOnlyId(@Param("userId") long userId);

    /*
    * 更新用户信息
    * */
    int updateUser(User user);

    /*
    * 通过邮箱验证
    * */
    User queryByOnlyEmail(@Param("email") String email,@Param("username") String username);

    /*
    * 查询所有用户信息
    * */
    List<User> queryAllUser();

    /*
    * 模糊查询用户列表
    * */
    List<User> selectUserByLike(@Param("key")String key);
}
