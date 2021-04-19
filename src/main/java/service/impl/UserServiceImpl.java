package service.impl;


import dao.RedisDao;
import dao.UserDao;
import dto.RegisterState;
import entity.User;
import enums.UserRegisterEnums;
import exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * getSalt MD5加密
 * @author pengliuyi
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //密码Md5加密的盐
    private final String salt = "auogahbvafihvoonafio993";

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisDao redisDao;


    /*
     * 优化：1.添加事务注解和事务的配置，当注册异常时回滚和正常时的提交。
     *       2.自定义异常类，出现相应的错误，抛出异常回滚。
     * */
    @Override
    @Transactional
    public RegisterState register(User user)
            throws UserException, UserExistException, UserMisssException, UserInsertException {
        String password = user.getUserPassword();
        user.setUserPassword(getSalt(password));
        String redisKey = "user:";
        try {
            User redisUser = redisDao.getUser(redisKey, user.getUserId());
            if (redisUser == null) {

                String res = redisDao.setUser(redisKey, user);
                logger.info("############pengliuyi专用日志########### 注册功能模块的插入Redis数据返回值：" + res);
                int insertCount = userDao.insertUser(user);
                if (insertCount <= 0) {
                    throw new UserInsertException(UserRegisterEnums.FAIL.getStateInfo());
                } else {
                    return new RegisterState(user.getUserId(), UserRegisterEnums.SUCCESS, user);
                }
            } else {//如果redis中已经存在，表示该用户名不能被注册
                throw new UserExistException(UserRegisterEnums.RedisEXIST.getStateInfo());
            }
        } catch (UserExistException existException) {
            return new RegisterState(user.getUserId(), UserRegisterEnums.RedisEXIST);
        } catch (UserMisssException e) {
            throw e;
        } catch (UserInsertException e) {
            logger.error(e.getMessage(), e);
            return new RegisterState(user.getUserId(), UserRegisterEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RegisterState(user.getUserId(), UserRegisterEnums.INNER_ERROR);
        }
    }

    @Override
    @Transactional
    public RegisterState profile(User user)
            throws UserException, UserExistException, UserMisssException, UserInsertException {
        String password = user.getUserPassword();
        user.setUserPassword(getSalt(password));
        String redisKey = "user:";
        try {
            User redisUser = redisDao.getUser(redisKey, user.getUserId());
            if (redisUser == null) {
                String res = redisDao.setUser(redisKey, user);
                logger.info("############pengliuyi专用日志########### 修改用户功能模块的插入Redis数据返回值：" + res);
                int updateCount = userDao.updateUser(user);
                if (updateCount <= 0) {
                    throw new UserInsertException(UserRegisterEnums.FAIL.getStateInfo());
                } else {
                    return new RegisterState(user.getUserId(), UserRegisterEnums.SUCCESS, user);
                }
            } else {//如果redis中存在 则出错，表示该用户名不能被修改
                throw new UserExistException(UserRegisterEnums.RedisEXIST.getStateInfo());
            }
        } catch (UserExistException existException) {
            return new RegisterState(user.getUserId(), UserRegisterEnums.RedisEXIST);
        } catch (UserMisssException e) {
            throw e;
        } catch (UserInsertException e) {
            logger.error(e.getMessage(), e);
            return new RegisterState(user.getUserId(), UserRegisterEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RegisterState(user.getUserId(), UserRegisterEnums.INNER_ERROR);
        }
    }
    @Override
    public RegisterState deleteUser(long userId) {
        int countDelete = userDao.deleteUser(userId);
        if(countDelete <= 0){
            return new RegisterState(userId, UserRegisterEnums.DELETEFAIL);
        }else
            try{
                User deletedUser = userDao.queryByOnlyId(userId);
                //若用户被删除时在线 删除loginMap中的该用户消息
                ServletContext application = session.getServletContext();
                @SuppressWarnings("unchecked")
                Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
                logger.info("############pengliuyi专用日志###########  删除用户功能模块的数据：" + deletedUser.getUserId());
                session.removeAttribute("logoutUser");
                loginMap.remove((int) deletedUser.getUserId());//在Loginmap中删除该用户的消息
                application.setAttribute("loginMap", loginMap);
                return new RegisterState(userId, UserRegisterEnums.SUCCESS);
            }
            catch (Exception e) {
            e.printStackTrace();
            logger.info("############pengliuyi专用日志###########  删除loginMap中用户出现问题");
            return new RegisterState(userId, UserRegisterEnums.DELETEFAIL);
            }
    }
    @Override
    public User selectByName(String username) {
        return userDao.queryByName(username);
    }

    @Override
    public User selectById(long userId) {
        return userDao.queryByOnlyId(userId);
    }

    @Override
    public User login(String username, String Password) {
        String password = getSalt(Password);
        return userDao.queryById(username, password);
    }

    @Override
    @Transactional
    public RegisterState updateUser(User user)
            throws UserException, UserUpdateException {
        try {
            User u = userDao.queryByOnlyId(user.getUserId());
            if (u == null) {
                return new RegisterState(user.getUserId(), UserRegisterEnums.NOTEXIST);
            } else {
                String pas = getSalt(user.getUserPassword());
                user.setUserPassword(pas);
                int updateCount = userDao.updateUser(user);
                if (updateCount <= 0) {
                    throw new UserUpdateException(UserRegisterEnums.UPDATEFAIL.getStateInfo());
                } else {
                    return new RegisterState(user.getUserId(), UserRegisterEnums.SUCCESS, user);
                }
            }
        } catch (UserUpdateException e1) {
            logger.error(e1.getMessage(), e1);
            return new RegisterState(user.getUserId(), UserRegisterEnums.UPDATEFAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RegisterState(user.getUserId(), UserRegisterEnums.INNER_ERROR);
        }
    }

    @Override
    public User selectByEmail(String email, String username) {
        return userDao.queryByOnlyEmail(email, username);
    }

    //加密
    private String getSalt(String userPassword) {
        String md5 = userPassword + '/' + salt;
        return DigestUtils.md5DigestAsHex(md5.getBytes());
    }
    @Override
    public void Logout(String Username) {
        User u = (User) session.getAttribute("user");
        try {
            session.removeAttribute("user");
            ServletContext application = session.getServletContext();
            @SuppressWarnings("unchecked")
            Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
            logger.info("############pengliuyi专用日志###########  注销功能模块的数据：" + u.getUserId());
            loginMap.remove((int) u.getUserId());
            application.setAttribute("loginMap", loginMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("############pengliuyi专用日志###########  注销功能模块的出现异常");
        }
    }

    /*
     * 查找用户是否在线 返回值：0不在线 1在线
     * */
    @Override
    public int isOnline(long userId){
        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")
        Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
        String sessionId = (String)loginMap.get((int)userId);//查看该用户是否正在在线
        if(sessionId == null) return 0;//0不在线
        return -1;//-1 在线
    }

    @Override
    public int ForceLogout(long userId) {
        //判断是否在线
        int isOnline = isOnline(userId);
        if(isOnline == 0) return 0;//0表示不在线 直接结束


        User u = (User) session.getAttribute("user");
        User logoutUser = userDao.queryByOnlyId(userId);
        try {
            /*if (u.getUserType() == 1)
                session.removeAttribute("user");*/
            ServletContext application = session.getServletContext();
            @SuppressWarnings("unchecked")
            Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
            logger.info("############pengliuyi专用日志###########  强制注销功能模块的数据：" + logoutUser.getUserId());
            session.removeAttribute("logoutUser");
            loginMap.remove((int) logoutUser.getUserId());//在Loginmap中删除该用户的消息
            application.setAttribute("loginMap", loginMap);
            logger.info("############pengliuyi专用日志###########  loginMap：" + loginMap);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("############pengliuyi专用日志###########  强制注销功能模块的出现异常");
            return -1;
        }
    }

    @Override
    public List<User> selectAllUser() {
        return userDao.queryAllUser();
    }

    @Override
    public List<User> selectUserByLike(String key) {
        return userDao.selectUserByLike(key);
    }
}
