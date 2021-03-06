package web.admin;

import dto.InsertNewState;
import dto.NewsResult;
import dto.RegisterState;
import entity.New;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserService;

import javax.servlet.http.HttpSession;

/**
 * AdminAccountController包含管理员操作界面账号管理功能模块。
 * 强制下线：POST/admin/ForceLogout
 *
 * @author pengliuyi
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminAccountController {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;


    /*
     * 删除用户
     * */
    @RequestMapping(value = "/DeleteUser")
    public String DeleteUser(long userId, RedirectAttributes attributes){
        User admin = (User)session.getAttribute("user");
        if(admin.getUserType()==1) {//检测当前操作用户是否为管理员
            RegisterState state = userService.deleteUser(userId);//删除用户
            if (state.getState() != 1) {//不成功
                NewsResult<User> result = new NewsResult<User>(false, state.getStateInfo());
                attributes.addFlashAttribute("result", result);
            } else {
                NewsResult<User> result = new NewsResult<User>(true, state.getStateInfo());
                attributes.addFlashAttribute("result", result);
            }
        }
        else {
            NewsResult<User> result = new NewsResult<>(false,"当前用户无该权限");
            attributes.addFlashAttribute("result", result);
        }
        return "redirect:/new/userList";
    }

    /*
     * 实现强制下线
     * */
    @RequestMapping(value = "/ForceLogout")
    public String ForceLogout(long userId, RedirectAttributes attributes) {
        User admin = (User)session.getAttribute("user");
        NewsResult<User> result = new NewsResult<>(false,"强制下线结果");//返回结果
        if(admin.getUserType()==1) {//检测当前操作用户是否为管理员
            int state = userService.ForceLogout(userId);//0不在线 1下线成功 -1下线失败
            switch (state){
                case 0: {
                    result = new NewsResult<User>(false, "该用户不在线");
                    break;
                }
                case 1:{
                    result = new NewsResult<User>(true, "下线成功");
                    break;
                }
                case -1:{
                    result = new NewsResult<User>(false, "下线失败");
                }
            }
        }
        else {
            result = new NewsResult<>(false,"当前用户无该权限");
        }
        attributes.addFlashAttribute("result", result);
        return "redirect:/new/userList";
    }
}
