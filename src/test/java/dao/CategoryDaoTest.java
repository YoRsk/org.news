package dao;

import entity.Category;
import entity.Comment;
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
    }

}
