package com.example.skr;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class comAdapter extends ArrayAdapter<comment> {
    private int resourceId;

    public comAdapter(Context context, int textViewResourceId, List<comment> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        comment comment = getItem(position);
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
     //判断查看回复按钮是不是没有，如果没有则是采用comment_item2的adapter模式，如果找得到，则采用comment_item的adapter的模式
    if((Button) view.findViewById(R.id.repeatButton)==null){

    }else{


        Button repeatButton = (Button) view.findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(view.getContext(),replyTo.class);
                view.getContext().startActivity(intent);
            }
        });
    }
        return  view;
    }

}
