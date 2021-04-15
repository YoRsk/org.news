package web;

import dto.*;
import entity.Category;
import entity.New;
import entity.User;
import enums.UserRegisterEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CategoryService;
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
    private UserService userService;

    @Autowired
    private NewService newService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpServletRequest request;


    /*
     * 跳转主页页面
     * */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        /*NewList newList = newService.selectIndexNew();*/ //好像没用
        List<NewsData> newsData = newService.selectAllNews();
        List<Category> categoryList = categoryService.queryAllCategory();
        /*model.addAttribute("list", newList);*/
        model.addAttribute("newData", newsData);
        model.addAttribute("categoryList",categoryList);
        return "NewIndex";
    }

    /*
     * 跳转到用户中心,未登录则跳转到登录
     * */
    @GetMapping(value = "/user/center")
    public String center(long userId, Model model) {
        User admin = (User)session.getAttribute("user");//当前操作用户 是否为本人或者管理员

        if(admin != null && (admin.getUserType() == 1|| admin.getUserId() == userId)){
            User user = userService.selectById(userId);
            List<NewsData> list = newService.selectNewsByUserId(user.getUserId());
            List<CommentData> list1 = commentService.selectCommentByUser(user.getUserId());
            model.addAttribute("user",user);
            model.addAttribute("news", list);
            model.addAttribute("comment", list1);
            return "NewsAndComment";
        }else {
            NewsResult<String> result = new NewsResult<>(false, "未登录");
            model.addAttribute("result", result);
            return "login";
        }

        /*if (user != null) {//表示已经登录
            List<NewsData> list = newService.selectNewsByUserId(user.getUserId());
            List<CommentData> list1 = commentService.selectCommentByUser(user.getUserId());
            model.addAttribute("news", list);
            model.addAttribute("comment", list1);
            return "NewsAndComment";
        } else {
            NewsResult<String> result = new NewsResult<>(false, "未登录");
            model.addAttribute("result", result);
            return "redirect:/user/login";
        }*/
    }
    /*
     * 跳转到具体新闻 记录浏览量
     * */
    @RequestMapping("/new/detail")
    public String detail(long newId, Model model) {
        NewDetail newsData = newService.selectNew(newId);
        newsData = newService.updateViews(newsData);//点击量+1
        List<CommentData> list = commentService.selectCommentByNew(newId);
        logger.info("************新闻详细信息的新闻数据***************" + newsData.getUser());
        logger.info("************新闻详细信息的评论数据***************" + list);
        model.addAttribute("detaildata", newsData);
        model.addAttribute("commentlist", list);
        return "newsdetail";
    }
    /*
     * 跳转到具体目录
     * */
    @RequestMapping("/category")
    public String cDetail(long categoryId,Model model){
        /*NewList newList = newService.selectIndexNew();*/
        List<NewsData> newsData = newService.selectNewsByCategoryId(categoryId);
        List<Category> categoryList = categoryService.queryAllCategory();
        String title = newsData.get(0).getTypeName();//把categories name 作为title
        /*model.addAttribute("list", newList);*/
        model.addAttribute("newData", newsData);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("title",title);
        return "NewIndex";
    }

    /*
     * 根据浏览次数排列热点新闻
     * */
   @RequestMapping("/hot")
    public String hot(Model model){
        List<NewsData> newsData = newService.selectHotNewsByViews();
        List<Category> categoryList = categoryService.queryAllCategory();
        model.addAttribute("newData", newsData);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("title","热点");
        return "NewIndex";

    }







}
