package service.impl;

import dao.CategoryDao;
import dao.CommentDao;
import dao.NewDao;
import dao.UserDao;
import dto.InsertNewState;
import dto.NewDetail;
import dto.NewList;
import dto.NewsData;
import entity.Category;
import entity.New;
import entity.User;
import enums.InsertNewEnums;
import exception.ContentMissException;
import exception.InsertNewException;
import exception.NewException;
import exception.UpdateNewException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.NewService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class NewServiceImpl implements NewService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewDao newDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    @Transactional
    public InsertNewState insertNew(New n)
            throws NewException, ContentMissException, InsertNewException {
        try {
            if (n == null) {
                throw new ContentMissException("插入数据不完整！");
            } else {
                New news = newDao.queryByNewName(n.getTitle());
                if (news != null) {
                    return new InsertNewState(n.getNewId(), InsertNewEnums.EXIST);
                } else {
                    int countInsert = newDao.insertNew(n);
                    if (countInsert <= 0) {
                        throw new InsertNewException(InsertNewEnums.FAIL.getStateinfo());
                    } else {
                        return new InsertNewState(n.getNewId(), InsertNewEnums.SUCCESS, n);
                    }
                }
            }
        } catch (InsertNewException e1) {
            logger.error(e1.getMessage(), e1);
            return new InsertNewState(n.getNewId(), InsertNewEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new InsertNewState(n.getNewId(), InsertNewEnums.INNER_ERROR);
        }
    }
    /*
     * index新闻list（未添加state==2判断)
     * */
    /*@Override
    public NewList selectIndexNew() {
        List<New> hotnew = newDao.queryByCategoryId(1);
        List<New> entertail = newDao.queryByCategoryId(2);
        List<New> tech = newDao.queryByCategoryId(3);
        List<New> military = newDao.queryByCategoryId(4);
        List<New> sport = newDao.queryByCategoryId(5);
        List<New> world = newDao.queryByCategoryId(6);
        NewList newList = new NewList();
        newList.setHOT_NEWS(hotnew);
        newList.setENTERTAINMENT_NEWS(entertail);
        newList.setSPORT_NEWS(sport);
        newList.setTECH_NEWS(tech);
        newList.setMILITARY(military);
        newList.setWORLD_NEWS(world);
        return newList;
    }*/
    @Override
    public NewDetail selectNew(long newId) {
        New news = newDao.queryByNewId(newId);
        NewDetail detail = new NewDetail();
        if (news != null) {
            User user = userDao.queryByOnlyId(news.getUserId());
            detail.setSuccess(true);
            detail.setaNew(news);
            detail.setUser(user);
        } else {
            detail.setSuccess(false);
        }
        return detail;
    }

    @Override
    public List<NewsData> selectNewsByUserId(long userId) {
        return newDao.queryByUserId(userId);
    }

    /*
     * 作者查询自己发表的新闻列表
     * */
    @Override
    public List<NewsData> selectNewsByCategoryId(long categoryId){return newDao.queryByCategoryId(categoryId);}

    /*
     * 根据浏览量选择热点
     * */
    @Override
    public List<NewsData> selectAllNews() {
        return newDao.queryAllNews();
    }

    /*
     * 根据热点排序新闻
     * */
    @Override
    public List<NewsData> selectHotNewsByViews(){ return newDao.queryHotNewsByViews();}

    /*
     * 删除指定新闻
     * 1.是用户本身删除自己的新闻：需要验证用户信息
     * 2.管理员删除不合格的新闻，验证用户是不是为管理员。
     * 3.将附带的评论一起删除
     * */
    @Override
    public InsertNewState deleteNew(long newId, User user) {
        New n = newDao.queryByNewId(newId);
        if (n.getUserId() == user.getUserId() || user.getUserType() == 1) {
            int countDelete = newDao.deleteNew(newId);
            int commentDelete = commentDao.deleteCommentByNewId(newId);
            if (countDelete <= 0 ||commentDelete <= 0) {
                return new InsertNewState(newId, InsertNewEnums.FAIL);
            } else {
                return new InsertNewState(newId, InsertNewEnums.SUCCESS, n);
            }
        } else {
            return new InsertNewState(newId, InsertNewEnums.UNOPERATION);
        }
    }

    @Override
    @Transactional
    public InsertNewState updateNews(New n)
            throws NewException, UpdateNewException {
        New aNew = newDao.queryByNewId(n.getNewId());
        try {
            if (aNew == null) {
                return new InsertNewState(n.getNewId(), InsertNewEnums.NOTEXIST);
            } else {
                int countUpdate = newDao.updateNew(n);
                if (countUpdate <= 0) {
                    throw new UpdateNewException(InsertNewEnums.FAIL.getStateinfo());
                } else {
                    return new InsertNewState(n.getNewId(), InsertNewEnums.SUCCESS, n);
                }
            }
        } catch (UpdateNewException e1) {
            logger.error(e1.getMessage(), e1);
            return new InsertNewState(n.getNewId(), InsertNewEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new InsertNewState(n.getNewId(), InsertNewEnums.INNER_ERROR);
        }

    }

    @Override
    @Transactional
    public InsertNewState updateState(New n)
            throws NewException, UpdateNewException{
        New aNew = newDao.queryByNewId(n.getNewId());
        try{
            if (aNew == null){
                return new InsertNewState(n.getNewId(),InsertNewEnums.NOTEXIST);
            } else {
                int countUpdate = newDao.updateState(n);
                if (countUpdate <= 0) {
                    throw new UpdateNewException(InsertNewEnums.FAIL.getStateinfo());
                } else {
                    return new InsertNewState(n.getNewId(), InsertNewEnums.SUCCESS, n);
                }
            }
        }catch (UpdateNewException e1) {
            logger.error(e1.getMessage(), e1);
            return new InsertNewState(n.getNewId(), InsertNewEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new InsertNewState(n.getNewId(), InsertNewEnums.INNER_ERROR);
        }
    }

    @Override
    public List<NewsData> selectNewsByLike(String key) {
        return newDao.selectByLike(key);
    }

    @Override
    public List<NewsData> selectNewsByLikeIndex(String key) {
        return newDao.selectByLikeIndex(key);
    }

    @Override
    public List<NewsData> selectNewsByKey(String key) {
        return newDao.selectByKeyWords(key);
    }

    @Override
    public New selectNewsBytitle(String title) {
        return newDao.queryByNewName(title);
    }

    @Override
    @Transactional
    public NewDetail updateViews(NewDetail nd){
        //synchronized和AtomicInteger解决并发问题
        AtomicInteger count = new AtomicInteger(0);
        New n = nd.getaNew();
        Category c = categoryDao.queryByCategoryId(n.getCategoryId());
        synchronized (n){
            count.getAndIncrement();
            n.setViews(n.getViews() + count.get());
            newDao.updateNew(n);
        }
        synchronized (c){
            c.setViewsNum(c.getViewsNum() + count.get());
            categoryDao.updateCategory(c);
        }
        return nd;
    }
}
