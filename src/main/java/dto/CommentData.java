package dto;

import entity.Comment;
import entity.New;
import entity.User;


public class CommentData {
    private boolean success;

    private Comment comment;

    private String userName;

    private String newtitle;

    public String getNewtitle() {
        return newtitle;
    }

    public void setNewtitle(String newtitle) {
        this.newtitle = newtitle;
    }

    @Override
    public String toString() {
        return "CommentData{" +
                "success=" + success +
                ", comment=" + comment +
                ", userName='" + userName + '\'' +
                ", newtitle='" + newtitle + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
