package service.impl;

import dao.CommentDao;
import dao.UserDao;
import dto.CommentData;
import dto.CommentState;
import entity.Comment;
import entity.User;
import enums.CommentEnums;
import exception.CommentException;
import exception.CommentInsertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CommentService;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public CommentState insertComment(Comment comment)
            throws CommentException, CommentInsertException {
        try {
            if (comment != null) {
                int count = commentDao.insertComment(comment);
                if (count <= 0)
                    throw new CommentInsertException(CommentEnums.INSERTFAIL.getStateInfo());
                return new CommentState(comment.getCommentId(), CommentEnums.SUCCESS, comment);
            } else {
                return new CommentState(comment.getCommentId(), CommentEnums.ERROR);
            }
        } catch (CommentInsertException e1) {
            logger.error(e1.getMessage(), e1);
            return new CommentState(comment.getCommentId(), CommentEnums.INSERTFAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CommentState(comment.getCommentId(), CommentEnums.INSERTFAIL);
        }

    }

    @Override
    public List<CommentData> selectCommentByNew(long newId) {
        return commentDao.queryCommentByNewId(newId);
    }

    @Override
    public List<CommentData> selectCommentByUser(long userId) {
        return commentDao.queryCommentByUserId(userId);
    }

    @Override
    public List<CommentData> selectAllComment() {
        return commentDao.queryAllComment();
    }

    @Override
    public List<CommentData> selectCommentByLike(String key) {
        return commentDao.selectCommentByLike(key);
    }

    @Override
    public Comment selectCommentById(long commentId) {
        return commentDao.queryCommentById(commentId);
    }

    @Override
    @Transactional
    public int deleteComment(long commentId, long userId) {
        Comment comment = commentDao.queryCommentById(commentId);
        User user = userDao.queryByOnlyId(userId);
        if (comment != null) {
            if (comment.getUserId() == userId ||user.getUserType() == 1) {
                int count = commentDao.deleteComment(commentId, userId);
                if (count <= 0)
                    return 0;
                return 1;
            } else {
                return 0;
            }
        }
        return 1;
    }
}
