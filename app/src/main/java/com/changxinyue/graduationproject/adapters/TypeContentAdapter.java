package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class TypeContentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ScenicInfo> scenicInfoList;
    private OnItemClickListener onItemClickListener;

    public TypeContentAdapter(Context context, List<ScenicInfo> scenicInfoList) {
        this.context = context;
        this.scenicInfoList = scenicInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<ScenicInfo> getScenicInfoList() {
        return scenicInfoList;
    }

    public void setScenicInfoList(List<ScenicInfo> scenicInfoList) {
        this.scenicInfoList = scenicInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_type_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (scenicInfoList.get(position).getScenic_image().length() == 0) {
            Glide.with(context).load(R.drawable.image_default).into(viewHolder.iv_type_content);
        } else {
            Glide.with(context).load(scenicInfoList.get(position).getScenic_image()).into(viewHolder.iv_type_content);
        }
        viewHolder.tv_type_content.setText(scenicInfoList.get(position).getScenic_name());
    }

    @Override
    public int getItemCount() {
        return scenicInfoList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_type_content;
        private TextView tv_type_content;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            initView(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            iv_type_content = itemView.findViewById(R.id.iv_type_content);
            tv_type_content = itemView.findViewById(R.id.tv_type_content);
            iv_type_content.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
