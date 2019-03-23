package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.MyselfInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;

import java.util.List;

public class MyselfAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MyselfInfo> myselfInfoList;
    private OnItemClickListener onItemClickListener;

    public MyselfAdapter(Context context, List<MyselfInfo> myselfInfoList) {
        this.context = context;
        this.myselfInfoList = myselfInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_myself, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myselfInfoList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}
