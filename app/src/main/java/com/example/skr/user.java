package com.example.skr;

import org.litepal.crud.DataSupport;

public class user extends DataSupport {
    private  String userName;//用户名
    private  String password;//用户密码
    private  String userAccount;//用户账号
    private  String portrait;//用户头像
    private  String userIntro;//个人简介
    private  String sex;//性别
    private  String  birthday; //出生年月

    public String getPassword() {
        return password;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getSex() {
        return sex;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public String getUserName() {
        return userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
