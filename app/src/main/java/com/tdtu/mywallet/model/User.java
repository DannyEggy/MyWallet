package com.tdtu.mywallet.model;

import java.util.List;

public class User {
    private String userAccount;

    private String userPassword;

    private String userRole;

    public User(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userRole = "member";
    }

    public User(String userAccount, String userPassword, String userRole) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
