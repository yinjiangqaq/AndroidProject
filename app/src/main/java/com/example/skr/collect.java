package com.example.skr;

import org.litepal.crud.DataSupport;

public class collect extends DataSupport {
    private String collect_id;//收藏id
    private String  post_id; //帖子id
    private String userAccount; //按收藏按钮的人的用户账号
    private  String collect_time;//帖子收藏时间

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public String getCollect_time() {
        return collect_time;
    }


}
