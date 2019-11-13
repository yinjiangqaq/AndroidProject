package com.example.skr;

public class reply {
    private  String  comment_id;//回复的那个评论的id

    private  String userAccount;//回复评论的用户的账户
    private  String reply_content;//回复的内容
    private  String reply_time;//回复时间

    public String getUserAccount() {
        return userAccount;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getReply_time() {
        return reply_time;
    }

    public String getComment_id() {
        return comment_id;
    }



    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }



    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }


}
