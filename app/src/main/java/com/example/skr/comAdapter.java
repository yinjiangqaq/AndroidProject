package com.example.skr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class comAdapter extends ArrayAdapter<comment> {
    private int resourceId;
    private  List<user> commentUser;
    private  String userAccount;//操作人
    public comAdapter(Context context, int textViewResourceId, List<comment> objects,String useraccount) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.userAccount=useraccount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //拿到评论的数据，并返回打印在评论组件上
        Connector.getDatabase();
        final comment comment = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        } else {
            view = convertView;
        }
        TextView comment_text  = (TextView) view.findViewById(R.id.message_card_topic_replied_detail);
        comment_text.setText(comment.getComment_content());
      TextView comment_time = (TextView) view.findViewById(R.id.message_card_user_time);
      comment_time.setText(comment.getComment_time());
      TextView comment_userName = (TextView) view.findViewById(R.id.message_card_user_name);
     String userComment =comment.getUserAccount();
     commentUser = DataSupport.where("userAccount=?",userComment).find(user.class);
     user commentuser1 = commentUser.get(0);
     String commentUserName =commentuser1.getUserName();
     comment_userName.setText(commentUserName);
     String commentUserPortrait = commentuser1.getPortrait();
        Bitmap bitmap =  BitmapFactory.decodeFile(commentUserPortrait);
        ImageView CommentUserPortrait = (ImageView) view.findViewById(R.id.message_card_user_head);
        CommentUserPortrait.setImageBitmap(bitmap);
     //判断查看回复按钮是不是没有，如果没有则是采用comment_item2的adapter模式，如果找得到，则采用comment_item的adapter的模式
    if((Button) view.findViewById(R.id.repeatButton)==null){

    }else{


        Button repeatButton = (Button) view.findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("userAccount",userAccount);
                intent.putExtra("comment_id",comment.getComment_id());
                intent.setClass(view.getContext(),replyTo.class);
                view.getContext().startActivity(intent);
            }
        });
    }
        return  view;
    }

}
