package com.example.skr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.ArrayList;
import java.util.List;

public class detail extends AppCompatActivity {
    private List<comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ThumbUpView mThumbUpView = (ThumbUpView) findViewById(R.id.like);
        mThumbUpView.setUnLikeType(ThumbUpView.LikeType.unlike);

        mThumbUpView.setCracksColor(Color.rgb(22, 33, 44));
        mThumbUpView.setFillColor(Color.rgb(230,0,0));
        mThumbUpView.setEdgeColor(Color.rgb(33, 3, 219));
        //判断是否点赞
        mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like==true){
                    Toast.makeText(detail.this,"点赞成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(detail.this,"取消点赞", Toast.LENGTH_SHORT).show();
                }
            }
        });



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
        comAdapter adapter = new comAdapter(detail.this,R.layout.comment_item,commentList);
        UnScrollListView  unScrollListView= (UnScrollListView) findViewById(R.id.Comment);
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

