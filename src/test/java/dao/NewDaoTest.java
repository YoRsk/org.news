package dao;

import dto.NewsData;
import entity.New;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangxin
 * @time 2018/12/24  20:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class NewDaoTest {

    @Autowired
    private NewDao newDao;

    @Test
    public void insertNewTest(){
        New n=new New(2,1009,"习近平的治国策略","fasdifjasodpaf","习近平",0,0);
        System.out.println(":"+newDao.insertNew(n));
    }

    @Test
    public void queryByNewIdTest(){
        int id=1;
        New aNew = newDao.queryByNewId(id);
        System.out.println("New:"+aNew);
    }

    @Test
    public void queryByCategoryIdTest(){
        int id=2;
        List<NewsData> list=newDao.queryByCategoryId(id);
        System.out.println("List<NewsData>:"+list);
    }
    @Test
    public void queryHotNewsByViews(){
        List<NewsData> list = newDao.queryHotNewsByViews();
        System.out.println("List<NewsData>:"+list);
    }

    @Test
    public void queryByUserIdTest(){
        int id=1009;
        List<NewsData> list=newDao.queryByUserId(id);
        System.out.println("List<New>:"+list);
    }

    @Test
    public void deleteNewTest(){
        int newId=1;
        int userId=1009;
        System.out.println("delete:"+newDao.deleteNew(newId));
    }

    @Test
    public void updateNewTest(){
        New n=new New(2,1009,"习近平","545","习近平22",0,0);
        n.setNewId(2);
        System.out.println(":"+newDao.updateNew(n));

    }

    @Test
    public void queryByNewNameTest(){
        String name="习近平";
        New aNew = newDao.queryByNewName(name);
        System.out.println("New:"+aNew);
    }

    @Test
    public void queryAllNewsTest(){
        List<NewsData> list=newDao.queryAllNews();
        System.out.println("List<New>:"+list);
    }

    @Test
    public void selectByLikeTest(){
        String key="yangxin";
        System.out.println("List<News>:"+newDao.selectByLike(key));
    }

    @Test
    public void selectByKeyWordsTest(){
        String key="视频";
        System.out.println("List<New>:"+newDao.selectByKeyWords(key));

    }



}