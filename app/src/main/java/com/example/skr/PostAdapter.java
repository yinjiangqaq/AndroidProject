package com.example.skr;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.UUID;

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
                ,home_post_collect_num;

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
            home_post_collect_num = (TextView) view.findViewById(R.id.home_post_collect_num);
        }

    }

    public PostAdapter(List<post> postList , String UserAccount){
        mpostList = postList;
        userAccount = UserAccount;
        Log.d("find bug", "userAccount: "+userAccount);
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
//        Log.d("find bug", "home_post_userAccount: "+home_post_userAccount);
        Connector.getDatabase();
        List<user> userList;
        userList = DataSupport.where("userAccount = ?",home_post_userAccount).find(user.class);
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
        holder.home_post_collect_num.setText(mpost.getPost_collect_num()+"");//收藏数

//        //判断本人是否有收藏过(弃用)
//        List<collect> collectList = DataSupport.where("post_id = ? and userAccount = ?",home_post_id,userAccount).find(collect.class);
//        if (collectList.size()==0||collectList==null){
//            holder.home_post_like_selected.setVisibility(View.GONE);
//            holder.home_post_like_notSelected.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.home_post_like_notSelected.setVisibility(View.GONE);
//            holder.home_post_like_selected.setVisibility(View.VISIBLE);
//        }

        //点击事件
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

//        //收藏
//        holder.home_post_like_notSelected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.home_post_like_notSelected.setVisibility(View.GONE);
//                holder.home_post_like_selected.setVisibility(View.VISIBLE);
//                List<collect> collectList = DataSupport.where("post_id = ? and userAccount = ?",home_post_id,userAccount).find(collect.class);
//                if (collectList.size()==0||collectList==null){
//                    collect newCollect = new collect();
//                    newCollect.setCollect_id(UUID.randomUUID().toString());
//                    newCollect.setPost_id(home_post_id);
//                    newCollect.setUserAccount(userAccount);
//                    newCollect.setCollect_time(MyApplication.getNowTime());
//                    newCollect.save();
////                    collectList.add(newCollect);
//                    mpost.setPost_collect_num(mpost.getPost_collect_num()+1);
//                    mpost.updateAll("post_id = ?",home_post_id);
//                    holder.home_post_collect_num.setText(mpost.getPost_collect_num()+"");
//                    XToast.success(v.getContext(),"收藏成功").show();
//                }
//                else {
////                    XToast.warning(v.getContext(),"请勿频繁操作").show();
//                }
//            }
//        });

////        取消收藏
//        holder.home_post_like_selected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.home_post_like_selected.setVisibility(View.GONE);
//                holder.home_post_like_notSelected.setVisibility(View.VISIBLE);
//                List<collect> collectList = DataSupport.where("post_id = ? and userAccount = ?",home_post_id,userAccount).find(collect.class);
//                if (collectList.size()==0||collectList==null){
////                    XToast.warning(v.getContext(),"请勿频繁操作").show();
//                }
//                else {
//                    collect newCollect = collectList.get(0);
//                    DataSupport.deleteAll(collect.class ,"post_id = ? and userAccount = ? ",home_post_id,userAccount);
////                    collectList.clear();
//                    mpost.setPost_collect_num(mpost.getPost_collect_num()-1);
//                    mpost.updateAll("post_id = ?",home_post_id);
//                    holder.home_post_collect_num.setText(mpost.getPost_collect_num()+"");
//                    XToast.success(v.getContext(),"取消收藏成功").show();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mpostList.size();
    }

}
