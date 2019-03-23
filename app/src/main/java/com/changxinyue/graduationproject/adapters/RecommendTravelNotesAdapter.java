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
import com.changxinyue.graduationproject.beans.TravelNoteInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class RecommendTravelNotesAdapter extends RecyclerView.Adapter<RecommendTravelNotesAdapter.ViewHolder> {
    private Context context;
    private List<TravelNoteInfo> data;
    private OnItemClickListener onItemClickListener;

    public RecommendTravelNotesAdapter(Context context, List<TravelNoteInfo> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommend_travel_notes, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate,onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(data.get(i).getImage() == null || data.get(i).getImage().length() == 0){
            Glide.with(context).load(R.drawable.image_default).into(viewHolder.iv_recommend);
        }else{
            Glide.with(context).load(data.get(i).getImage()).into(viewHolder.iv_recommend);
        }
        viewHolder.tv_recommend_title.setText(data.get(i).getTitle());
        viewHolder.tv_recommend_abstract.setText(data.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_recommend;
        private TextView tv_recommend_title;
        private TextView tv_recommend_abstract;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            initView(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        private void initView(View itemView) {
            iv_recommend = itemView.findViewById(R.id.iv_recommend);
            tv_recommend_title = itemView.findViewById(R.id.tv_recommend_title);
            tv_recommend_abstract = itemView.findViewById(R.id.tv_recommend_abstract);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getPosition());
        }
    }
}
