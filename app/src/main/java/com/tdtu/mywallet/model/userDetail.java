package com.tdtu.mywallet.model;

import com.tdtu.mywallet.R;

import java.util.List;

public class userDetail {
    private String userAccount;
    private String userName;
    private int userAvatar;
    private float userBalance;
    private List<Activity> activityList;

    public userDetail(String userAccount, String userName, List<Activity> activityList) {
        int avatarResID = R.drawable.user;
        this.userAccount = userAccount;
        this.userName = userName;
        this.activityList = activityList;
        this.userAvatar = avatarResID;
        this.userBalance = 0;
    }

    public userDetail(String userAccount, String userName, int userAvatar, float userBalance, List<Activity> activityList) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userBalance = userBalance;
        this.activityList = activityList;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }

    public float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(float userBalance) {
        this.userBalance = userBalance;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
