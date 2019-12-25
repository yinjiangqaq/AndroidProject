package com.example.skr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class replyTo extends AppCompatActivity {
    private List<comment> commentList = new ArrayList<>();
 private  String userAccount;//回复的那个评论的id
 private  String  comment_id;//回复的那个评论的id
    private List<user> users;
    private boolean lock=false;
  EditText replytText;

    TextView commentTime;
    ImageView userPortrait;
    TextView commentText;
    TextView commentUserName;
    TextView replyNum;
private  List<reply>replyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replyto);





    }
    @Override
    public void onStart() {
        Connector.getDatabase();//连接数据库
        Intent intent = getIntent();
        userAccount= intent.getStringExtra("userAccount");//拿到操作人的userAccount
        comment_id = intent.getStringExtra("comment_id");
        commentText =(TextView) findViewById(R.id.comment_replied_detail) ;
        commentTime = (TextView) findViewById(R.id.comment_replied_userTime) ;
        commentUserName =(TextView)  findViewById(R.id.comment_replied_userName) ;
        userPortrait =(ImageView) findViewById(R.id.comment_replied_userPortrait) ;
        replytText = (EditText) findViewById(R.id.reply_detail) ;
        replyNum =(TextView) findViewById(R.id.replyNum);
        initComment();
        super.onStart();

       Button submmit = (Button) findViewById(R.id.sub_reply);
       submmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(lock){
                   //do nothing
               }
               else{

                   if(replytText.getText().toString().trim().isEmpty()){
                       XToast.warning(replyTo.this,"回复不能为空").show();
                   }
                   else {
                       lock=true;

                       reply reply = new reply();
                       reply.setReply_content(replytText.getText().toString());
                       reply.setComment_id(comment_id);

                       reply.setUserAccount(userAccount);//操作人的
                       reply.setReply_time(MyApplication.getNowTime());
                       reply.save();
                       XToast.success(replyTo.this,"回复成功").show();
                       replytText.setText("");
                       lock=false;
                       onStart();


                   }
               }
           }
       });

        //  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Comment);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));//一定要加manager
        // PostAdapter adapter = new PostAdapter(snackList);
        // commentAdapter adapter = new commentAdapter(commentList);
        //recyclerView.setAdapter(adapter);
        replyAdapter adapter = new  replyAdapter(replyTo.this,R.layout.comment_item2,replyList,userAccount);
        UnScrollListView  unScrollListView= (UnScrollListView) findViewById(R.id.Reply);
        unScrollListView.setAdapter(adapter);
    }
    private  void initComment(){
        commentList =  DataSupport.where("comment_id=?",comment_id).find(comment.class);

        comment comment = commentList.get(0);
        commentText.setText(comment.getComment_content());
        commentTime.setText(comment.getComment_time());
        String userComment = comment.getUserAccount();
        users = DataSupport.where("userAccount=?",userComment).find(user.class);
        user user = users.get(0);
        commentUserName.setText(user.getUserName()+"     ("+"楼主"+")");
        Bitmap bitmap2 = BitmapFactory.decodeFile(user.getPortrait());
        userPortrait.setImageBitmap(bitmap2);
//        for(int i =0; i<20;i++){
//            comment Comment = new comment();
//            Comment.setComment_content("你好嘛,发的是解放军ask飞机喀什就发生客服就爱上升级附加赛附件是发顺丰是否接受甲方");
//            Comment.setComment_time("2019-11-20");
//            commentList.add(Comment);
//        }
     replyList = DataSupport.where("comment_id=?",comment_id).find(reply.class);
     replyNum.setText(replyList.size()+"条回复");
    }
}
