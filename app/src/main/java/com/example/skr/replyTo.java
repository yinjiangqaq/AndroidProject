package com.example.skr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class replyTo extends AppCompatActivity {
    private List<comment> commentList = new ArrayList<>();
 private  String userAccouont;//回复的那个评论的id
 private  String  comment_id;//回复的那个评论的id
EditText replytText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replyto);





    }
    @Override
    public void onStart() {
        Connector.getDatabase();//连接数据库
        initComment();
        super.onStart();
        Intent intent = getIntent();
        userAccouont= intent.getStringExtra("userAccount");
        comment_id = intent.getStringExtra(" comment_id");

        //  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Comment);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));//一定要加manager
        // PostAdapter adapter = new PostAdapter(snackList);
        // commentAdapter adapter = new commentAdapter(commentList);
        //recyclerView.setAdapter(adapter);
        comAdapter adapter = new comAdapter(replyTo.this,R.layout.comment_item2,commentList);
        UnScrollListView  unScrollListView= (UnScrollListView) findViewById(R.id.Reply);
        unScrollListView.setAdapter(adapter);
    }
    private  void initComment(){
//        for(int i =0; i<20;i++){
//            comment Comment = new comment();
//            Comment.setComment_content("你好嘛,发的是解放军ask飞机喀什就发生客服就爱上升级附加赛附件是发顺丰是否接受甲方");
//            Comment.setComment_time("2019-11-20");
//            commentList.add(Comment);
//        }

    }
}
