package com.example.skr;

import org.litepal.crud.DataSupport;

public class comment extends DataSupport {
    private  String  comment_id;//评论的id
    private String post_id;//帖子的id
    private  String userAccount;//评论的用户的账户
    private  String comment_content;//评论的内容
    private  String comment_time;//评论时间

    public String getUserAccount() {
        return userAccount;
    }

    public String getComment_content() {
        return comment_content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getComment_time() {
        return comment_time;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
