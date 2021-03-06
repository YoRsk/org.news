package dao;

import entity.Category;
import entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class CategoryDaoTest {
    @Autowired
    private CategoryDao categoryDao;
    @Test
    public void query(){
        List<Category> list = categoryDao.queryAllCategory();
        System.out.println(list);
        for (Category category : list) {
            System.out.println(category.getCategoryName());
        }

    }

    @Test
    public void deleteCategory(){
        categoryDao.deleteCategory(2);
    }
    @Test
    public void updateAllId(){
        categoryDao.deleteAllId();
        categoryDao.updateAllId();
      /*  List<Category> list = categoryDao.queryAllCategory();
        for (Category category : list) {
            System.out.println(category.getCategoryId());
        }*/
    }

}
