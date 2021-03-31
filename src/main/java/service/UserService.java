package service;

import dto.RegisterState;
import entity.User;

import java.util.List;

public interface UserService {

    /*
    * 注册用户，默认是普通用户，不能是管理员
    * */
    RegisterState register(User user);

    /*
    * 根据用户名验证用户是否存在
    * */
    User selectByName(String userName);

    /*
    * 登录验证，用户名，密码
    * */
    User login(String userName,String Password);

    /*
    * 用户更新密码
    * */
    RegisterState updateUser(User user);

    /*
    * 通过邮箱验证用户
    * */
    User selectByEmail(String email,String username);

    /*
    * 强制下线
    * */
    void ForceLogout(String UserName);

    /*
     * 主动下线
     * */
    void Logout(String UserName);

    /*
    * 查询所有用户信息
    * */
    List<User> selectAllUser();

    /*
    * 模糊查找
    * */
    List<User> selectUserByLike(String key);
}
