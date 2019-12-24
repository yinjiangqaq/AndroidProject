package com.example.skr;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.List;

public class replyTo extends AppCompatActivity {
    private List<comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replyto);





    }
    @Override
    public void onStart() {
        initComment();
        super.onStart();
        //  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Comment);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));//一定要加manager
        // SnackAdapter adapter = new SnackAdapter(snackList);
        // commentAdapter adapter = new commentAdapter(commentList);
        //recyclerView.setAdapter(adapter);
        comAdapter adapter = new comAdapter(replyTo.this,R.layout.comment_item2,commentList);
        UnScrollListView  unScrollListView= (UnScrollListView) findViewById(R.id.Reply);
        unScrollListView.setAdapter(adapter);
    }
    private  void initComment(){
        for(int i =0; i<20;i++){
            comment Comment = new comment();
            Comment.setComment_content("你好嘛,发的是解放军ask飞机喀什就发生客服就爱上升级附加赛附件是发顺丰是否接受甲方");
            Comment.setComment_time("2019-11-20");
            commentList.add(Comment);
        }
    }
}
