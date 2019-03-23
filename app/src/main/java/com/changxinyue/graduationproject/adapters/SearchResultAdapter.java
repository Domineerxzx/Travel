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

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.activities.ScenicActivity;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> data;

    public SearchResultAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_search_result, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search_result:
            case R.id.tv_search_result_title:
            case R.id.tv_search_result_abstract:
                Intent intent = new Intent(context, ScenicActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_search_result;
        private TextView tv_search_result_title;
        private TextView tv_search_result_abstract;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setOnClickListener();
        }

        private void initView(View itemView) {
            iv_search_result = itemView.findViewById(R.id.iv_recommend);
            tv_search_result_title = itemView.findViewById(R.id.tv_recommend_title);
            tv_search_result_abstract = itemView.findViewById(R.id.tv_recommend_abstract);

        }

        private void setOnClickListener() {
            iv_search_result.setOnClickListener(SearchResultAdapter.this);
            tv_search_result_title.setOnClickListener(SearchResultAdapter.this);
            tv_search_result_abstract.setOnClickListener(SearchResultAdapter.this);
        }
    }
}
