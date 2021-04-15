package web.admin;

import dto.CommentData;
import dto.NewsData;
import entity.Category;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CategoryService;
import service.CommentService;
import service.NewService;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*AdminNavController:管理员界面跳转
* 管理员主页:/new/adminIndex
 * 评论页：/new/commentList
 * 用户页：/new/userList
 * 目录页：/new/categoryList
 * @author:pengliuyi
* */
@RequestMapping(value = "/new")
@Controller
public class AdminNavController {

    @Autowired
    private HttpSession session;

    @Autowired
    private NewService newService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/adminIndex")
    public String adminIndex(Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<NewsData> list = newService.selectAllNews();
            model.addAttribute("Newslist", list);
            return "adminIndex";
        }else{
            return "redirect:/admin/login";
        }
    }

    @GetMapping(value = "/commentList")
    public String commentList(Model model){
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<CommentData> commentData = commentService.selectAllComment();
            model.addAttribute("commentList", commentData);
            return "AdminIndexComment";
        }else{
            return "redirect:/admin/login";
        }
    }

    @GetMapping(value = "/userList")
    public String userlist(Model model){
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<User> list = userService.selectAllUser();
            for (User u: list) {
                int state = userService.isOnline(u.getUserId());
                if(state == 0)//不在线
                u.setIsOnline(0);
                else u.setIsOnline(1);//在线
            }
            model.addAttribute("userList", list);
            return "AdminIndexUser";
        }else{
            return "redirect:/admin/login";
        }
    }
    @GetMapping(value = "/categoryList")
    public String categoryList(Model model){
        User user = (User) session.getAttribute("user");
        if(user != null) {
            List<Category> list = categoryService.queryAllCategory();
            model.addAttribute("categoryList",list);
            return "AdminIndexCategory";
        }else{
            return "redirect:/admin/login";
        }
    }
}
