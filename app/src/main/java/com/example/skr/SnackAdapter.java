package com.example.skr;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {
   // private List<snack> snackList;
    private List<post> snackList;
    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView snackImage;
        TextView snackTitle;
        public    ViewHolder(View view){
            super(view);
            snackImage = (ImageView)view.findViewById(R.id.snack_image);
            snackTitle = (TextView)view.findViewById(R.id.snack_title);
        }

    }
    public SnackAdapter(List<post> SnackList){snackList = SnackList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_item,parent,false);

       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final post Snack = snackList.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(Snack.getPost_image());
        holder.snackImage.setImageBitmap(bitmap);
        holder.snackTitle.setText(Snack.getPost_title());
//在这里实现snackitem的页面跳转，跳转到详情页面
        holder.snackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(),detail.class);
                intent.putExtra("userAccount",Snack.getUserAccount());
                intent.putExtra("post_id",Snack.getPost_id());
                view.getContext().startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return snackList.size();
    }



}
