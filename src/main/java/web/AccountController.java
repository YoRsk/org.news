package web;

import dto.NewsResult;
import dto.ResgisterState;
import entity.User;
import enums.UserRegisterEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * AccountController包含有注册、忘记密码等账号管理模块。
 * 采用RESTful设计URL接口：
 * 注册：POST/user/toRegister
 * 更新：POST/user/update
 * 找回密码：POST/user/toForgetPassword
 * 注销接口：POST/user/Logout
 * @author pengliuyi
 */
@Controller
@RequestMapping(value = "/user")
public class AccountController {
    //日志打印。
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;


    /*
     * 跳转到注册页面
     * */
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    /*
     * 跳转到找回密码
     * */
    @GetMapping(value = "/forgetPassword")
    public String forgetpassword() {
        return "ForgetPassword";
    }


    /*
     * 实现注册逻辑.
     * 1：优化，在极端时间中，同时注册相同Username(原数据库中没有)，会导致同时插入非法数据。
     *    解决思路：使用Redis做二级缓存，注册时先要去缓存中去查找，如果有，那么已经被注册，表示不能注册。
     *               如果没有，注册成功的时候也往redis中插入。
     *
     *               已经处理事务
     * */
    @PostMapping(value = "/toRegister")
    public String toRegister(User user, Model model) {
        logger.info("############pengliuyi专用日志###########  注册功能模块的前台传来的注册数据：" + user);
        User existUser = userService.selectByName(user.getUserName());
        if (existUser != null) {//说明昵称已经存在
            NewsResult<User> register = new NewsResult<User>(false, UserRegisterEnums.DBAEXIST.getStateInfo());
            model.addAttribute("result", register);
            return "register";
        } else {
            user.setCreateTime(new Date());
            ResgisterState res = userService.register(user);
            if (res.getState() != 1) {//表示不成功
                NewsResult<User> register = new NewsResult<User>(false, res.getStateInfo());
                model.addAttribute("result", register);
                return "register";
            } else {
                NewsResult<User> register = new NewsResult<User>(true, user);
                model.addAttribute("result", register);
                session.setAttribute("user", user);
                return "login";
            }
        }
    }

    /*
     * 忘记密码找回密码逻辑
     * */
    @PostMapping(value = "/toForgetPassword")
    public String toForgetPassword(String username, String email, String password, Model model) {
        User user = userService.selectByEmail(email, username);
        if (user != null) {
            user.setUserPassword(password);
            logger.info(username + "," + email + "," + password);
            ResgisterState state = userService.updateUser(user);
            if (state.getState() != 1) {
                NewsResult<User> forget = new NewsResult<User>(false, state.getStateInfo());
                model.addAttribute("result", forget);
                return "ForgetPassword";
            } else {
                NewsResult<User> forget = new NewsResult<User>(true, user);
                model.addAttribute("result", forget);
                return "login";
            }
        } else {
            NewsResult<User> forget = new NewsResult<User>(false, UserRegisterEnums.NOTEXIST.getStateInfo());
            model.addAttribute("result", forget);
            return "ForgetPassword";
        }
    }

    /*
     * 实现注销登录(主页退出 包括正常用户和管理员
     * */
    @RequestMapping(value = "/Logout")
    public String Logout(String userName, Model model) {
        User usercheckLog = userService.selectByName(userName);
        userService.Logout(userName);
        NewsResult<User> result = new NewsResult<User>(true, "注销成功");
        model.addAttribute("resultLogout", result);
        if (usercheckLog.getUserType() == 1)
            return "redirect:/admin/login";
        return "redirect:/user/login";
    }
}
