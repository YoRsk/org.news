package web;

import dto.*;
import entity.Comment;
import entity.New;
import entity.User;
import enums.CommentEnums;
import enums.InsertNewEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CommentService;
import service.NewService;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/new")
public class NewController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private NewService newService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/adminIndex")
    public String adminIndex(Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<NewsData> list = newService.selectAllNews();
            model.addAttribute("Newslist", list);
            return "adminIndex";
        }else{
            return "redirect:/user/adminLogin";
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
            return "redirect:/user/adminLogin";
        }
    }

    @GetMapping(value = "/userList")
    public String userlist(Model model){
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            List<User> list = userService.selectAllUser();
            model.addAttribute("userList", list);
            return "AdminIndexUser";
        }else{
            return "redirect:/user/adminLogin";
        }
    }

    @RequestMapping(value = "deletecomment")
    public String deleteComment(long commentId, String userName, Model model) {
        //  logger.info("############yangxin专用日志###########  XX功能模块的XX数据："+);
        User user = userService.selectByName(userName);
        User login= (User) session.getAttribute("user");
        Comment comment = commentService.selectCommentById(commentId);
        if (login.getUserType() == 1 || user.getUserId() == login.getUserId()) {
            int i = commentService.deleteComment(commentId, user.getUserId());
            if (i <= 0) {
                NewsResult<Comment> result = new NewsResult<Comment>(false, CommentEnums.FAIL.getStateInfo());
                model.addAttribute("editResult", result);
            } else {
                //如果不是作者本人或者是管理员那么不允许修改文章。
                NewsResult<Comment> result = new NewsResult<Comment>(true, CommentEnums.SUCCESS.getStateInfo());
                model.addAttribute("editResult", result);
            }
        } else {
            //如果不是作者本人或者是管理员那么不允许修改文章。
            NewsResult<Comment> result = new NewsResult<Comment>(false, CommentEnums.UNOPERATION.getStateInfo());
            model.addAttribute("editResult", result);
        }
        if (login.getUserType() == 2)
            return "redirect:/new/commentList";
        else
            return "redirect:/user/index";
    }

    @RequestMapping("/submitcomment")
    public String submitComment(String commentContent, long newId, Model model) {
        logger.info("*************提交评论获取内容：" + commentContent + newId);
        User user = (User) session.getAttribute("user");
        logger.info("**************目前是否登录：" + user);
        if (user != null) {//目前已经登录
            Comment comment = new Comment();
            comment.setContent(commentContent);
            comment.setNewId(newId);
            comment.setUserId(user.getUserId());
            comment.setCreateTime(new Date());
            CommentState commentState = commentService.insertComment(comment);
            if (commentState.getState()!=1) {//插入失败
                NewsResult<Comment> result = new NewsResult<Comment>(false, CommentEnums.INTERBUSY.getStateInfo());
                model.addAttribute("insertComment", result);
            } else {
                NewsResult<Comment> result = new NewsResult<Comment>(true, CommentEnums.SUCCESS.getStateInfo());
                model.addAttribute("insertComment", result);
            }
        } else {//表示未登录
            NewsResult<Comment> result = new NewsResult<Comment>(false, CommentEnums.UNLOGIN.getStateInfo());
            model.addAttribute("insertComment", result);
            return "redirect:/user/login";
        }
        return "redirect:/new/detail?newId=" + newId;
    }

    @RequestMapping(value = "/editor")
    public String editor(int index) {
        User user = (User) session.getAttribute("user");
        if (user != null) {//表示已经登录
            return "editor";
        }else{
            if(index==1)
            return "redirect:/user/login";
            return "redirect:/user/adminLogin";
        }
    }

    @RequestMapping("/detail")
    public String detail(long newId, Model model) {
        NewDetail newsData = newService.selectNew(newId);
        List<CommentData> list = commentService.selectCommentByNew(newId);
        logger.info("************新闻详细信息的新闻数据***************" + newsData.getUser());
        logger.info("************新闻详细信息的评论数据***************" + list);
        model.addAttribute("detaildata", newsData);
        model.addAttribute("commentlist", list);
        return "newsdetail";
    }

    @RequestMapping(value = "/submitContent")
    public String toSubmit(New news, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            news.setUserId(user.getUserId());
            news.setViews(0);
            news.setStates(0);//0默认待编辑
            //先确定有没有这个片文章再插入。
            New aNew = newService.selectNewsBytitle(news.getTitle());
            if(aNew!=null){//表示已经存在
                NewsResult<New> result = new NewsResult<New>(false, InsertNewEnums.EXIST.getStateinfo());
                model.addAttribute("insertNewResult", result);
                return "editor";
            }else{
                InsertNewState state = newService.insertNew(news);
                if (state.getState() != 1) {//表示失败
                    NewsResult<New> result = new NewsResult<New>(false, state.getStateInfo());
                    model.addAttribute("insertNewResult", result);
                    return "editor";
                } else {
                    NewsResult<New> result = new NewsResult<New>(true, news);
                    model.addAttribute("insertNewResult", result);
                    if(user.getUserType()==2)
                    return "redirect:/new/adminIndex";
                    return "redirect:/user/index";
                }
            }
        } else {
            NewsResult<New> result = new NewsResult<New>(false, InsertNewEnums.UNLOGIN.getStateinfo());
            model.addAttribute("insertNewResult", result);
            return "editor";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(long newId, String userName,int tag, Model model) {
        logger.info("****************" + newId + "," + userName);
        User user = userService.selectByName(userName);
        InsertNewState state = newService.deleteNew(newId, user);
        if (state.getState() != 1) {//不成功
            NewsResult<New> result = new NewsResult<New>(false, state.getStateInfo());
            model.addAttribute("result", result);
        } else {
            NewsResult<New> result = new NewsResult<New>(true, state.getStateInfo());
            model.addAttribute("result", result);
        }
        if(tag==1)
            return "redirect:/user/center";
            return "redirect:/new/adminIndex";
    }

    @RequestMapping(value = "/edit")
    public String editNew(long newId, String userName, Model model) {
        User user = userService.selectByName(userName);
        User useradmin = (User) session.getAttribute("user");
        NewDetail detail = newService.selectNew(newId);
        New news = detail.getaNew();
        logger.info("############pengliuyi专用日志###########  修改新闻功能模块的XX数据："+user);
        if (useradmin.getUserType() == 1 || user.getUserId() == useradmin.getUserId()) {
            NewsResult<NewDetail> result = new NewsResult<NewDetail>(true, detail);
            model.addAttribute("editResult", result);
            return "editNews";
        } else {
            //如果不是作者本人或者是管理员那么不允许修改文章。
            NewsResult<New> result = new NewsResult<New>(false, InsertNewEnums.UNOPERATION.getStateinfo());
            model.addAttribute("editResult", result);
            if (useradmin.getUserType() == 1)
                return "redirect:/new/adminIndex";
            else
                return "redirect:/user/index";
        }
    }

    @RequestMapping(value = "/update")
    public String toupdate(New news, Model model) {
        User user = (User) session.getAttribute("user");
     //   User useradmin = (User) session.getAttribute("user");
        if (user != null) {
            news.setUserId(user.getUserId());
            InsertNewState state = newService.updateNews(news);
            if (state.getState() != 1) {//更新失败
                NewsResult<New> newNewsResult = new NewsResult<New>(false, state.getStateInfo());
                model.addAttribute("updateResult", newNewsResult);
                return "editNews";
            } else {
                NewsResult<New> newNewsResult = new NewsResult<New>(true, news);
                model.addAttribute("updateResult", newNewsResult);
                if(user.getUserType()!=1)
                    return "redirect:/user/center";
                return "redirect:/new/adminIndex";
            }
        } else {
            NewsResult<New> newNewsResult = new NewsResult<New>(false, InsertNewEnums.UNLOGIN.getStateinfo());
            model.addAttribute("updateResult", newNewsResult);
            return "editNews";
        }
    }

    /*
    * 模糊查询新闻
    * */
    @RequestMapping(value = "/selectByLike")
    public String selectNewsByLike(String selectkey,Model model){
        logger.info("############yangxin专用日志###########  模糊查询功能模块的前台返回的字段数据："+selectkey);
        if(!selectkey.equals("")){
            List<NewsData> newsData = newService.selectNewsByLike(selectkey);
            model.addAttribute("Newslist",newsData);
            return "adminIndex";
        }
        else{
            return "redirect:/new/adminIndex";
        }
    }

    /*
    * 模糊查询评论
    * */
    @RequestMapping(value = "/selectCommentByLike")
    public String selectCommentByLike(String selectkey,Model model){
        logger.info("############yangxin专用日志###########  模糊查询功能模块的前台返回的字段数据："+selectkey);
        if(!selectkey.equals("")){
            List<CommentData> commentData = commentService.selectCommentByLike(selectkey);
            model.addAttribute("commentlist",commentData);
            return "AdminIndexComment";
        }
        else{
            return "redirect:/new/commentlist";
        }
    }

    /*
     * 模糊查询用户
     * */
    @RequestMapping(value = "/selectUserByLike")
    public String selectUserByLike(String selectkey,Model model){
        logger.info("############pengliuyi专用日志###########  模糊查询功能模块的前台返回的字段数据："+selectkey);
        if(!selectkey.equals("")){
            List<User> list = userService.selectUserByLike(selectkey);
            model.addAttribute("userList",list);
            return "AdminIndexUser";
        }
        else{
            return "redirect:/new/userList";
        }
    }

    /*
     * 关键字查询新闻
     * */
    @RequestMapping(value = "/selectByKey")
    public String selectByKey(String selectkey,Model model){
        logger.info("############pengliuyi###########  模糊查询功能模块的前台返回的字段数据："+selectkey);
        if(!selectkey.equals("")){
            List<NewsData> newsData = newService.selectNewsByKey(selectkey);
            model.addAttribute("Newslist",newsData);
            return "adminIndex";
        }
        else{
            return "redirect:/new/adminIndex";
        }
    }

    /*
     * 实现强制下线
     * */
    @RequestMapping(value = "/ForceLogout")
    public String ForceLogout(String userName, Model model) {
        userService.ForceLogout(userName);
        NewsResult<User> result = new NewsResult<User>(true, "注销成功");
        model.addAttribute("resultLogout", result);
        return "redirect:/new/userList";
    }

}
