package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class PhotoWallAdapter extends RecyclerView.Adapter<PhotoWallAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private OnItemClickListener onItemClickListener;

    public PhotoWallAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhotoWallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_photo_wall, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate,onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i)).into(viewHolder.iv_photo_wall);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_photo_wall;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_photo_wall = itemView.findViewById(R.id.iv_photo_wall);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
