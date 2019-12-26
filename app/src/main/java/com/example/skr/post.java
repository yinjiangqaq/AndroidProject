package com.example.skr;

import org.litepal.crud.DataSupport;

public class post extends DataSupport {
    private String  post_id; //帖子id
    private String userAccount; //用户账号
    private String post_title;//帖子标题
    private  String post_content;//帖子内容
    private  String post_image;//帖子照片
    private  String post_time;//帖子发布时间
    private int post_collect_num;//收藏数

    public String getUserAccount() {
        return userAccount;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getPost_content() {
        return post_content;
    }

    public String getPost_image() {
        return post_image;
    }

    public String getPost_title() {
        return post_title;
    }

    public String getPost_time() {
        return post_time;
    }

    public int getPost_collect_num() {
        return post_collect_num;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public void setPost_collect_num(int post_collect_num) {
        this.post_collect_num = post_collect_num;
    }

}
