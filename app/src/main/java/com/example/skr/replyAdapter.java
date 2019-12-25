package com.example.skr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class replyAdapter extends ArrayAdapter<reply> {
    private int resourceId;
    private List<user> replyUser;
    private  String userAccount;//操作人
    public replyAdapter(Context context, int textViewResourceId, List<reply> objects, String useraccount) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.userAccount=useraccount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //拿到评论的数据，并返回打印在评论组件上
        Connector.getDatabase();
        final reply reply = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        } else {
            view = convertView;
        }
        TextView reply_text  = (TextView) view.findViewById(R.id.message_card_topic_replied_detail);
        reply_text.setText(reply.getReply_content());
        TextView reply_time = (TextView) view.findViewById(R.id.message_card_user_time);
        reply_time.setText(reply.getReply_time());
        TextView reply_userName = (TextView) view.findViewById(R.id.message_card_user_name);
        String userReply =reply.getUserAccount();
        replyUser = DataSupport.where("userAccount=?",userReply).find(user.class);
        user replyuser1 = replyUser.get(0);
        String commentUserName =replyuser1.getUserName();
        reply_userName.setText(commentUserName);
        String commentUserPortrait = replyuser1.getPortrait();
        Bitmap bitmap =  BitmapFactory.decodeFile(commentUserPortrait);
        ImageView CommentUserPortrait = (ImageView) view.findViewById(R.id.message_card_user_head);
        CommentUserPortrait.setImageBitmap(bitmap);


        return  view;
    }
}
