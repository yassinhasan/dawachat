package com.myproject.dawachat.Models;

public class User {
    private String userName , userEmail , userPassword ,userPic , userId , userStatus , userLastMsg ;
    public User(){};


    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(String userName, String userEmail, String userPassword, String userPic, String userId, String userStatus, String userLastMsg) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPic = userPic;
        this.userId = userId;
        this.userStatus = userStatus;
        this.userLastMsg = userLastMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserLastMsg() {
        return userLastMsg;
    }

    public void setUserLastMsg(String userLastMsg) {
        this.userLastMsg = userLastMsg;
    }
}
