package dao;

import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangxin
 * @time 2018/12/24  16:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void insertUserTest(){
        User user=new User(2,"王玉32","123456a","855566@163.com",18);
        System.out.println(userDao.insertUser(user));
    }

    @Test
    public void queryByIdTest(){
        User user=userDao.queryById("yangxin","123");
        System.out.println("User:"+user);
    }

    @Test
    public void updateUserTest(){
        User user=new User(2,"yangxin","123","7574",18);
        System.out.println(":"+userDao.updateUser(user));
    }

    @Test
    public void queryByNameTest(){
        User user=userDao.queryByName("yangxin");
        System.out.println("User:"+user);
    }
    @Test
    public void queryByOnlyIdTest(){
        User user=userDao.queryByOnlyId(1007);
        System.out.println("User:"+user);
    }

    @Test
    public void queryByOnlyEmailTest(){
        System.out.println("User:"+userDao.queryByOnlyEmail("782527347@qq.com","pengliuyi"));
    }

    @Test
    public void queryAllUserTest(){
        System.out.println("List<user>:"+userDao.queryAllUser());
    }

    @Test
    public void selectUserByLikeTest(){
        String key="yangxin";
        System.out.println("List<User>:"+userDao.selectUserByLike(key));
    }


}
