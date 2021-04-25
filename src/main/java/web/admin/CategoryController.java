package web.admin;

import dto.CategoryState;
import dto.NewsData;
import dto.NewsResult;
import entity.Category;
import entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CategoryService;
import service.NewService;
import sun.dc.pr.PRError;
import utils.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    public String  insertCategory(String categoryName, RedirectAttributes model){
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
            model.addFlashAttribute("result", result);
        }else {
            NewsResult<Category> result = new NewsResult<>(false,"当前用户无该权限");
            model.addFlashAttribute("result", result);
        }
        return "redirect:/new/categoryList";
    }

    /**
     * 导出报表
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取数据
        List<Category> list = categoryService.queryAllCategory();
        //excel标题
        String[] title = {"目录id","目录名", "该栏目的总点击数","该栏目下最火的新闻"};
        //excel文件名
        String fileName = "访问统计表" + System.currentTimeMillis() + ".xls";


        //sheet名
        String sheetName = "访问目录统计表";
        String[][] content = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            Category obj = list.get(i);
            content[i][0] = String.valueOf(obj.getCategoryId());
            content[i][1] = obj.getCategoryName();
            content[i][2] = String.valueOf(obj.getViewsNum());
        }


        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
