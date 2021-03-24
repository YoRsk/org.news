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
 * NavController导航界面跳转
 * 用户中心/user/center
 * 主页/index
 * @author pengliuyi
 * @time 2021/3/14  13:11
 */
@Controller
@RequestMapping
public class NavController {
    //日志打印。
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private NewService newService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpServletRequest request;


    /*
     * 跳转主页页面
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
     * 跳转到用户中心,未登录则跳转到登录
     * */
    @GetMapping(value = "/user/center")
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








}
