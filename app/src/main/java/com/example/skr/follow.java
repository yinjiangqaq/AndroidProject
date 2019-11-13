package com.example.skr;

public class follow {
    private  String userAccount;//点关注的人
    private  String userFollowed;//被关注的人

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setUserFollowed(String userFollowed) {
        this.userFollowed = userFollowed;
    }

    public String getUserFollowed() {
        return userFollowed;
    }
}
