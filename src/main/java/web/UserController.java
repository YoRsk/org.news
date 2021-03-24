package web;

import dto.*;
import entity.User;
import enums.UserRegisterEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CommentService;
import service.NewService;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController包含有登录、注册、用户信息修改三个模块。
 * 采用RESTful设计URL接口：
 * 登录接口 ：POST/login
 * 注册：POST/user/resgister
 * 更新：POST/user/update
 * @author pengliuyi
 * @time 2021/3/14  13:11
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    //日志打印。
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private NewService newService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;



    /*
     * 跳转主页页面，目前弃用
     * */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        NewList newList = newService.selectIndexNew();
        List<NewsData> newsData = newService.selectAllNews();
        model.addAttribute("list", newList);
        model.addAttribute("newData", newsData);
        return "NewIndex";
    }

    /*
     * 跳转到找回密码
     * */
    @GetMapping(value = "/forgetPassword")
    public String forgetpassword() {
        return "ForgetPassword";
    }

    /*
     * 跳转到注册页面
     * */
    @GetMapping(value = "/register")
    public String register() {
        return "register";
    }

    /*
     * 跳转到用户中心,未登录则条状到登录
     * */
    @GetMapping(value = "/center")
    public String center(Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<NewsData> list = newService.selectNewsByUserId(user.getUserId());
            List<CommentData> list1 = commentService.selectCommentByUser(user.getUserId());
            model.addAttribute("news", list);
            model.addAttribute("comment", list1);
            return "NewsAndComment";
        } else {
            NewsResult<String> result = new NewsResult<>(false, "未登录");
            model.addAttribute("result", result);
            return "redirect:/user/login";
        }
    }

    /*
     * 忘记密码找回密码逻辑
     * */
    @PostMapping(value = "/toForgetPassword")
    public String toForgetPassword(String username, String email, String password, Model model) {
        User user = userService.selectByEmail(email, username);
        user.setUserPassword(password);
        logger.info(username + "," + email + "," + password);
        if (user != null) {
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






}
