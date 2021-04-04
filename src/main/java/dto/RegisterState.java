package dto;

import entity.User;
import enums.UserRegisterEnums;

//user表更新状态（不仅限于register一个功能...
public class RegisterState {
    private long userId;

    private int state;

    private String stateInfo;

    private User user;


    public RegisterState(long userId, UserRegisterEnums resgisterState) {
        this.userId = userId;
        this.state = resgisterState.getState();
        this.stateInfo = resgisterState.getStateInfo();
    }


    public RegisterState(long userId, UserRegisterEnums resgisterState, User user) {
        this.userId = userId;
        this.state = resgisterState.getState();
        this.stateInfo = resgisterState.getStateInfo();
        this.user = user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResgisterState{" +
                "userId=" + userId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", user=" + user +
                '}';
    }


}
