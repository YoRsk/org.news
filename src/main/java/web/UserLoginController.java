package web;

import dto.NewsResult;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/*
 * 登录 登出 等
 * */
@Controller
@RequestMapping(value = "/user")
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    /*
     * 跳转登录页面
     * */
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
    /*
     * 跳转到管理员登录
     * */
    @GetMapping(value = "/adminLogin")
    public String adminLogin() {
        return "AdminLogin";
    }


    /*
     * 实现登录逻辑
     * */
    @PostMapping(value = "/login")
    public String toLogin(String username,
                          String password, Model model) {
        User userCheck = userService.selectByName(username);
        //检查是否已经登录
        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")//取消类型强制转换的警告
                Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
        if (loginMap == null) {
            loginMap = new HashMap<Integer, Object>();
        }
        //表示没有登录冲突
        User user = userService.login(username, password);
        if (user != null) {
            //说明有用户，表示用户或者密码正确
            NewsResult<User> result = new NewsResult<User>(true, user);
            model.addAttribute("result", result);
            //将登录信息存入application
            loginMap.put((int) userCheck.getUserId(), session.getId());
            application.setAttribute("loginMap", loginMap);
            // 将用户保存在session当中
            session.setAttribute("user", userCheck);
            // session 销毁时间
            session.setMaxInactiveInterval(10 * 60);
            return "redirect:/user/index";
        } else {
            NewsResult<User> result = new NewsResult<User>(false, "用户名或者密码错误");
            model.addAttribute("result", result);
            return "login";
        }
    }

    /*
     * 实现管理员登录逻辑
     * */
    @PostMapping(value = "/toAdminLogin")
    public String toAdminLogin(String username, String password, Model model) {
        User adminuserCheck = userService.selectByName(username);
        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")
        Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");
        if (loginMap == null) {
            loginMap = new HashMap<Integer, Object>();
        }
/*        for (int key : loginMap.keySet()) {
            if (adminuserCheck.getUserId() == key) {
                if (session.getId().equals(loginMap.get(key))) {
                    NewsResult<User> result = new NewsResult<User>(false,
                            adminuserCheck.getUserName() + "在同一地点重复登录");
                    model.addAttribute("adminresult", result);
                    return "adminIndex";//已登录，返回用户首页
                } else {
                    NewsResult<User> result = new NewsResult<User>(false,
                            adminuserCheck.getUserName() + "异地已登录，请先退出登录");
                    model.addAttribute("adminresult", result);
                    return "AdminLogin";
                }
            }
        }*/
        User user = userService.login(username, password);
        if (user != null && user.getUserType() == 1) {
            NewsResult<User> result = new NewsResult<User>(true, user);
            model.addAttribute("adminresult", result);
            model.addAttribute("customer", user);
            //将登录信息存入application
            loginMap.put((int) adminuserCheck.getUserId(), session.getId());
            application.setAttribute("loginMap", loginMap);
            // 将用户保存在session当中
            session.setAttribute("user", adminuserCheck);
            // session 销毁时间
            session.setMaxInactiveInterval(10 * 60);
            return "redirect:/new/adminIndex";
        } else {
            NewsResult<User> result = new NewsResult<User>(false, "用户名或者密码错误或者你不是管理员");
            model.addAttribute("adminresult", result);
            return "AdminLogin";
        }
    }

    /*
     * 实现注销登录
     * */
    @RequestMapping(value = "/Logout")
    public String Logout(String userName, Model model) {
        User usercheckLog = userService.selectByName(userName);
        userService.Logout(userName);
        NewsResult<User> result = new NewsResult<User>(true, "注销成功");
        model.addAttribute("resultLogout", result);
        if (usercheckLog.getUserType() == 1)
            return "redirect:/user/adminLogin";
        return "redirect:/user/login";
    }

}
