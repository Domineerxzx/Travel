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
import com.changxinyue.graduationproject.activities.CitiesActivity;
import com.changxinyue.graduationproject.activities.ScenicActivity;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class RecommendCitiesAdapter extends RecyclerView.Adapter<RecommendCitiesAdapter.ViewHolder> {
    private Context context;
    private List<CityInfo> cityInfoList;
    private OnItemClickListener onItemClickListener;

    public RecommendCitiesAdapter(Context context, List<CityInfo> cityInfoList) {
        this.context = context;
        this.cityInfoList = cityInfoList;
    }

    public List<CityInfo> getCityInfoList() {
        return cityInfoList;
    }

    public void setCityInfoList(List<CityInfo> cityInfoList) {
        this.cityInfoList = cityInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommend_cities, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_recommend_abstract.setText(cityInfoList.get(i).getCity_content());
        viewHolder.tv_recommend_cities_name.setText(cityInfoList.get(i).getCity_name());
        if (cityInfoList.get(i).getCity_image() == null || cityInfoList.get(i).getCity_image().length() == 0) {
            Glide.with(context).load(R.drawable.image_default).into(viewHolder.iv_recommend);
        } else {
            Glide.with(context).load(cityInfoList.get(i).getCity_image()).into(viewHolder.iv_recommend);
        }
    }

    @Override
    public int getItemCount() {
        return cityInfoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_recommend;
        private TextView tv_recommend_cities_name;
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
            tv_recommend_cities_name = itemView.findViewById(R.id.tv_recommend_cities_name);
            tv_recommend_abstract = itemView.findViewById(R.id.tv_recommend_abstract);
        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
