package web.admin;

import dto.CategoryState;
import dto.NewsData;
import dto.NewsResult;
import entity.Category;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CategoryService;
import service.NewService;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/category")
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NewService newService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/deleteCategory")
    public String  deleteCategory(long categoryId, RedirectAttributes attributes){
        User admin = (User)session.getAttribute("user");
        if(admin.getUserType() == 1 ){
            //删除该目录下所有的新闻以及评论 要先删，因为删完目录后 id会重新排序
            List<NewsData> list= newService.selectNewsByCategoryId(categoryId);
            for (NewsData nd:
                    list) {
                newService.deleteNew(nd.getaNew().getNewId(),admin);
            }
            //删除目录 重新排序id
            CategoryState state = categoryService.deleteCategory(categoryId);
            NewsResult<Category> result;
            if (state.getState() != 1) {//不成功
                result = new NewsResult<>(false, state.getStateInfo());
            } else {
                result = new NewsResult<>(true, state.getStateInfo());
            }
            attributes.addFlashAttribute("result", result);
        }else {
            NewsResult<Category> result = new NewsResult<>(false,"当前用户无该权限");
            attributes.addFlashAttribute("result", result);
        }
        return "redirect:/new/categoryList";
    }

    @RequestMapping("/insertCategory")
    public String  insertCategory(String categoryName, Model model){
        User admin = (User)session.getAttribute("user");
        if(admin.getUserType() == 1 ){
            Category category = new Category(categoryName);
            CategoryState state = categoryService.insertCategory(category);
            NewsResult<Category> result;
            if (state.getState() != 1) {//不成功
                result = new NewsResult<>(false, state.getStateInfo());
            } else {
                result = new NewsResult<>(true, state.getStateInfo());
            }
            model.addAttribute("result", result);
        }else {
            NewsResult<Category> result = new NewsResult<>(false,"当前用户无该权限");
            model.addAttribute("result", result);
        }
        return "AdminIndexCategory";
    }
}
