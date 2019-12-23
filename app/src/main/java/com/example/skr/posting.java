package com.example.skr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.litepal.LitePalApplication.getContext;

public class posting extends AppCompatActivity {

    EditText posting_title,posting_content;
    ImageView posting_selectImageButton;
    Button posting_postButton;

    private String[] imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        Connector.getDatabase();

        posting_title = (EditText) findViewById(R.id.posting_title);
        posting_content = (EditText) findViewById(R.id.posting_content);
        posting_selectImageButton = (ImageView) findViewById(R.id.posting_selectImageButton);
        posting_selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        posting_postButton = (Button) findViewById(R.id.posting_postButton);
        posting_postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUITipDialog tipDialog;
                if(posting_title.getText().toString().trim().isEmpty()||posting_content.getText().toString().trim().isEmpty()){
                    //提示完善信息
                    tipDialog = new QMUITipDialog.Builder(posting.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                            .setTipWord("请完善信息")
                            .create();
                    tipDialog.show();
                    posting_postButton.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tipDialog.dismiss();
                        }
                    },1500);
                }
                else {
                    //存储帖子，页面跳转
                    post myNewPost = new post();
                    myNewPost.setPost_id(UUID.randomUUID().toString());
                    myNewPost.setUserAccount("20173068");                           //此处要改
                    myNewPost.setPost_title(posting_title.getText().toString());
                    myNewPost.setPost_content(posting_content.getText().toString());
//                    myNewPost.setPost_image("");                                    //此处要改
                    myNewPost.setPost_time("2020/1/1");                             //此处要改
                    myNewPost.save();

                    //提示发送成功
                    tipDialog = new QMUITipDialog.Builder(posting.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("发帖成功")
                            .create();
                    tipDialog.show();
//                    posting_postButton.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            tipDialog.dismiss();
//                        }
//                    },1500);

                    posting_postButton.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1500);


                    //退出此页面
//                    finish();


                }
            }
        });

    }



}
