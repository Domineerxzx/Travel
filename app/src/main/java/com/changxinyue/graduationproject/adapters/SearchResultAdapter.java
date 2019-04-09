package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.activities.ScenicActivity;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<ScenicInfo> scenicInfoList;
    private OnItemClickListener onItemClickListener;

    public SearchResultAdapter(Context context, List<ScenicInfo> scenicInfoList) {
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
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        SearchResultAdapter.ViewHolder viewHolder = new SearchResultAdapter.ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (SearchResultAdapter.ViewHolder) viewHolder;
        if (scenicInfoList.get(i).getScenic_image().length() == 0) {
            Glide.with(context).load(R.drawable.image_default).into(holder.iv_search_result);
        } else {
            Glide.with(context).load(scenicInfoList.get(i).getScenic_image()).into(holder.iv_search_result);
        }
        holder.tv_search_result_title.setText(scenicInfoList.get(i).getScenic_name());
        holder.tv_search_result_abstract.setText(scenicInfoList.get(i).getScenic_content());
    }


    @Override
    public int getItemCount() {
        return scenicInfoList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener onItemClickListener;
        private ImageView iv_search_result;
        private TextView tv_search_result_title;
        private TextView tv_search_result_abstract;

        public ViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            initView(itemView);
            this.onItemClickListener = SearchResultAdapter.this.onItemClickListener;
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            iv_search_result = itemView.findViewById(R.id.iv_search_result);
            iv_search_result.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tv_search_result_title = itemView.findViewById(R.id.tv_search_result_title);
            tv_search_result_abstract = itemView.findViewById(R.id.tv_search_result_abstract);
        }

        @Override
        public void onClick(View v) {
            SearchResultAdapter.this.onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
