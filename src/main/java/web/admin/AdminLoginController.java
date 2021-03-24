package web.admin;

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
 * 登录接口 ：POST/admin/login
 * */
@Controller
@RequestMapping(value = "/admin")
public class AdminLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    /*
     * 跳转到管理员登录
     * */
    @GetMapping(value = "/login")
    public String adminLogin() {
        return "AdminLogin";
    }

    /*
     * 实现管理员登录逻辑
     * */
    @PostMapping(value = "/login")
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
}
