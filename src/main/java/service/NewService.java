package service;

import dto.InsertNewState;
import dto.NewDetail;
import dto.NewList;
import dto.NewsData;
import entity.New;
import entity.User;

import java.util.List;


public interface NewService {

    /*
    * 插入新新闻
    * */
    InsertNewState insertNew(New n);

    /*
    * 获取主页的所有类型的新闻。(未判断是否发表State==2)
    * *//*
    NewList selectIndexNew();*/

    /*
    * 获取新闻的详情信息。新闻内容、作者信息
    * */
    NewDetail selectNew(long newId);

    /*
    * 作者查询自己发表的新闻列表
    * */
    List<NewsData> selectNewsByUserId(long userId);

    /*
     * 作者查询自己发表的新闻列表
     * */
    List<NewsData> selectNewsByCategoryId(long categoryId);

    /*
    * 管理员查看所有发表新闻
    * */
    List<NewsData> selectAllNews();

    /*
     * 根据热点排序新闻
     * */
    List<NewsData> selectHotNewsByViews();

    /*
    * 删除指定新闻
    * 1.是用户本身删除自己的新闻：需要验证用户信息
    * 2.管理员删除不合格的新闻，验证用户是不是为管理员。
    * */
    InsertNewState deleteNew(long newId, User user);

    /*
    * 更新新闻内容
    * */
    InsertNewState updateNews(New n);
    /*
     * 更新新闻状态
     * */
    InsertNewState updateState(New n);
    /*
    * 模糊查询
    * */
    List<NewsData> selectNewsByLike(String key);

    /*
     * 关键字查询
     * */
    List<NewsData> selectNewsByKey(String key);

    /*
     * 根据标题查询
     * */
    New selectNewsBytitle(String title);

    /*
     * 点击时增加点击量
     * */
    NewDetail updateViews(NewDetail n);

}
