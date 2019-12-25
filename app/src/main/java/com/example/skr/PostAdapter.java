package com.example.skr;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<post> mpostList;
    String userAccount;//操作人的userAccount

    static class ViewHolder extends  RecyclerView.ViewHolder{
        View home_post_view;
        ImageView home_post_image
                ,home_post_user_head
                ,home_post_like_notSelected
                ,home_post_like_selected;
        TextView home_post_title
                ,home_post_user_name
                ,home_post_time
                ,home_post_hotNumber;

        public    ViewHolder(View view){
            super(view);
            home_post_view = (LinearLayout) view.findViewById(R.id.home_post_view);
            home_post_image = (ImageView) view.findViewById(R.id.home_post_image);
            home_post_user_head = (ImageView) view.findViewById(R.id.home_post_user_head);
            home_post_like_notSelected = (ImageView) view.findViewById(R.id.home_post_like_notSelected);
            home_post_like_selected = (ImageView) view.findViewById(R.id.home_post_like_selected);
            home_post_title = (TextView) view.findViewById(R.id.home_post_title);
            home_post_user_name = (TextView) view.findViewById(R.id.home_post_user_name);
            home_post_time = (TextView) view.findViewById(R.id.home_post_time);
            home_post_hotNumber = (TextView) view.findViewById(R.id.home_post_hotNumber);

        }

    }

    public PostAdapter(List<post> postList , String UserAccount){
        mpostList = postList;
        userAccount = UserAccount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //onClick
//        holder.home_post_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(parent.getContext(),detail.class);
//            }
//        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final post mpost = mpostList.get(position);
        final String home_post_id = mpost.getPost_id();

        //获得发帖人user信息
        String home_post_userAccount = mpost.getUserAccount();
        Connector.getDatabase();
        List<user> userList;
        userList = DataSupport
                .where("userAccount=?",home_post_userAccount)
                .find(user.class);
        if (userList==null||userList.size()==0){
            Log.d("In postAdapter:", "onBindViewHolder: 得不到userList, home_post_userAccount="+home_post_userAccount);
        }else {
            user pUser = userList.get(0);
            Bitmap bitmapFor_home_post_user_head = BitmapFactory.decodeFile(pUser.getPortrait());
            holder.home_post_user_head.setImageBitmap(bitmapFor_home_post_user_head);
            holder.home_post_user_name.setText(pUser.getUserName());
        }

        //获得帖子信息
        Bitmap bitmap = BitmapFactory.decodeFile(mpost.getPost_image());
        holder.home_post_image.setImageBitmap(bitmap);
        holder.home_post_title.setText(mpost.getPost_title());
        holder.home_post_time.setText(mpost.getPost_time());
        //缺热度和点赞

        //
        holder.home_post_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),detail.class);
                intent.putExtra("post_id",home_post_id);
                intent.putExtra("userAccount",userAccount);
                Log.d("postAdapter", "post_id : "+home_post_id);
                Log.d("postAdapter", "userAccount : "+userAccount);
                v.getContext().startActivity(intent);
            }
        });

        //在这里实现snackitem的页面跳转，跳转到详情页面
//        holder.snackImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(view.getContext(),detail.class);
//                intent.putExtra("userAccount",Snack.getUserAccount());
//                intent.putExtra("post_id",Snack.getPost_id());
//                view.getContext().startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mpostList.size();
    }

}
