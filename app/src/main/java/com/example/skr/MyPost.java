package com.example.skr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MyPost extends AppCompatActivity {

    String userAccount;
    private List<post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        userAccount=intent.getStringExtra("userAccount");

        Connector.getDatabase();
        initSnack();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ranking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//一定要加manager
        PostAdapter adapter = new PostAdapter(postList,userAccount);
        recyclerView.setAdapter(adapter);
    }

    private  void initSnack(){
        //采用LitePal在查询数据的时候进行对收藏数进行降序排序，返回对应的post数组
        postList= DataSupport.where("userAccount = ?",userAccount).find(post.class);
    }

}
