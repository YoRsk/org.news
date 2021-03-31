package dao;

import dto.CommentData;
import entity.Comment;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;


public interface CommentDao {
    /*
    * 添加评论
    * */
    int insertComment(Comment comment);

    /*
    * 根据新闻获取评论
    * */
    List<CommentData> queryCommentByNewId(@Param("newId") long newId);

    /*
     * 获取所有评论
     * */
    List<CommentData> queryAllComment();

    /*
    * 显示用户参与的评论
    * */
    List<CommentData> queryCommentByUserId(@Param("userId") long userId);

    /*
     * 根据id查询
     * */
    Comment queryCommentById(@Param("commentId") long commentId);

    /*
    * 用户自己或管理员删除评论
    * */
    int deleteComment(@Param("commentId") long commentId,@Param("userId") long userId);

    /*
     * 删除某新闻下的所有评论
     * */
    int deleteCommentByNewId(@Param("newId")long newId);

    /*
     * 模糊查询
     * */
    List<CommentData> selectCommentByLike(@Param("key") String key);
}
