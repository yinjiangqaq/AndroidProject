package com.example.skr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {
    private List<snack> snackList;
    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView snackImage;
        TextView snackTitle;
        public    ViewHolder(View view){
            super(view);
            snackImage = (ImageView)view.findViewById(R.id.snack_image);
            snackTitle = (TextView)view.findViewById(R.id.snack_title);
        }

    }
    public SnackAdapter(List<snack> SnackList){snackList = SnackList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_item,parent,false);
       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        snack Snack = snackList.get(position);
        holder.snackImage.setImageResource(Snack.getImageId());
        holder.snackTitle.setText(Snack.getName());
    }



    @Override
    public int getItemCount() {
        return snackList.size();
    }



}
