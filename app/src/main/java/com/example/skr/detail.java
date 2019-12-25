package com.example.skr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ldoublem.thumbUplib.ThumbUpView;
import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class detail extends AppCompatActivity {
    private List<comment> commentList = new ArrayList<>();
    private  String userAccount;//操作人的userAccount
    private  String post_id;
    private  List <post> posts;
    private  List<user> postUser;//作者的
    ImageView postImage;
    TextView postuserName;
    TextView postTitle;
    TextView postContent;
    TextView postTime;
    ImageView userPortrait;
    TextView commentText;
    private boolean lock=false;
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
        Connector.getDatabase();
        Intent intent = getIntent();
        userAccount= intent.getStringExtra("userAccount");//操作本人的
        post_id = intent.getStringExtra("post_id");
        postImage = (ImageView) findViewById(R.id.post_image);
        postuserName = (TextView)findViewById(R.id.post_user_name);
        postContent =(TextView) findViewById(R.id.post_content) ;
        postTime =(TextView) findViewById(R.id.post_user_time);
        postTitle =(TextView) findViewById(R.id.post_title) ;
        userPortrait =(ImageView)findViewById(R.id.post_user_head) ;
        commentText =(TextView) findViewById(R.id.message_card_topic_replied_detail);
         initComment();
        super.onStart();
      //  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Comment);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));//一定要加manager
       // PostAdapter adapter = new PostAdapter(snackList);
       // commentAdapter adapter = new commentAdapter(commentList);
       //recyclerView.setAdapter(adapter);
        Button submmit = (Button)findViewById(R.id.sub_comment);
        submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lock){
                    //do nothing
                }
                else{
                    if(commentText.getText().toString().trim().isEmpty()){
                        XToast.warning(detail.this,"评论不能为空").show();
                    }
                    else {
                        lock=true;
                        //存储帖子，页面跳转
                   //  String userAccount_operator = (String) MyApplication.infoMap.get("userAccount");//通过全局变量拿本人操作者
//                        post myNewPost = new post();
//                        myNewPost.setPost_id(UUID.randomUUID().toString());
//                        myNewPost.setUserAccount(userAccount);                           //此处要改
//                        myNewPost.setPost_title(posting_title.getText().toString());
//                        myNewPost.setPost_content(posting_content.getContentText());
//                        myNewPost.setPost_image(posting_imagePath);                                    //此处要改
//                        myNewPost.setPost_time("2020/1/1");                             //此处要改
//                        myNewPost.save();
           comment comment = new comment();
            comment.setComment_content(commentText.getText().toString());
             comment.setComment_id(UUID.randomUUID().toString());
            comment.setPost_id(post_id);
            comment.setUserAccount(userAccount);//操作人的
            comment.setComment_time(MyApplication.getNowTime());
            comment.save();
            XToast.success(detail.this,"评论成功").show();
            commentText.setText("");
            lock=false;
            onStart();


                    }
                }
            }
        });

        comAdapter adapter = new comAdapter(detail.this,R.layout.comment_item,commentList,userAccount);//把操作人的userAccount传进Adapter
        UnScrollListView  unScrollListView= (UnScrollListView) findViewById(R.id.Comment);
        unScrollListView.setAdapter(adapter);

    }
    private  void initComment(){
//        for(int i =0; i<20;i++){
//            comment Comment = new comment();
//            Comment.setComment_content("你好嘛,发的是解放军ask飞机喀什就发生客服就爱上升级附加赛附件是发顺丰是否接受甲方");
//            Comment.setComment_time("2019-11-20");
//            commentList.add(Comment);
//        }
        //在数据库里找到对应的post和user，然后把他们的信息输出来
posts = DataSupport.where("post_id=?",post_id).find(post.class);


post post=posts.get(0);
String post_image = post.getPost_image();
String post_title = post.getPost_title();
String post_content = post.getPost_content();
String post_time  = post.getPost_time();
String post_userAccount = post.getUserAccount();
postUser = DataSupport.where("userAccount=?",post_userAccount).find(user.class);//作者的
user postuser = postUser.get(0);
String post_userName = postuser.getUserName();
String post_userPortrait = postuser.getPortrait();
commentList = DataSupport.where("post_id=?",post_id).find(comment.class);
Bitmap bitmap = BitmapFactory.decodeFile(post_image);
postImage.setImageBitmap(bitmap);
postuserName.setText(post_userName);
postContent.setText(post_content);
postTime.setText(post_time);
postTitle.setText(post_title);
Bitmap bitmap2 = BitmapFactory.decodeFile(post_userPortrait);
userPortrait.setImageBitmap(bitmap2);

    }
    }

