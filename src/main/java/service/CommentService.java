package service;

import dto.CommentData;
import dto.CommentState;
import entity.Comment;

import java.util.List;


public interface CommentService {
    /*
    * 添加评论
    * */
    CommentState insertComment(Comment comment);

    /*
    * 获取文章下的评论
    * */
    List<CommentData> selectCommentByNew(long newId);

    /*
     * 获取用户参与的评论
     * */
    List<CommentData> selectCommentByUser(long userId);

    /*
     * 获取所有的评论
     * */
    List<CommentData> selectAllComment();

    /*
     * 模糊查询
     * */
    List<CommentData> selectCommentByLike(String key);

    /*
     * 根据ID获取评论
     * */
   Comment selectCommentById(long commentId);

    /*
    * 用户自己或者管理员删除评论
    * */
    int deleteComment(long commentId,long userId);
}
