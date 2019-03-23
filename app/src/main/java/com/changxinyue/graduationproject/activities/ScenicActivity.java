package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.TypeContentAdapter;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.ScenicManager;

import java.io.Serializable;
import java.util.List;

public class ScenicActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private ImageView iv_back;
    private TextView tv_scenic_name;
    private ImageView iv_scenic_image;
    private TextView tv_scenic_content;
    private RecyclerView rv_other_scenic;
    private ScenicInfo scenicInfo;
    private ScenicManager scenicManager;
    private List<ScenicInfo> scenicInfoList;
    private TypeContentAdapter typeContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
    }

    private void initData() {
        scenicManager = new ScenicManager(this);
        Intent intent = getIntent();
        scenicInfo = (ScenicInfo) intent.getSerializableExtra("ScenicInfo");
        tv_scenic_name.setText(scenicInfo.getScenic_name());
        if(scenicInfo.getScenic_image().length() == 0){
            Glide.with(this).load(R.drawable.image_default).into(iv_scenic_image);
        }else{
            Glide.with(this).load(scenicInfo.getScenic_image()).into(iv_scenic_image);
        }
        tv_scenic_content.setText(scenicInfo.getScenic_content());
        scenicInfoList = scenicManager.getScenicInfoList(scenicInfo.getCity_name());
        typeContentAdapter = new TypeContentAdapter(this, scenicInfoList);
        typeContentAdapter.setOnItemClickListener(this);
        rv_other_scenic.setAdapter(typeContentAdapter);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_scenic_name = (TextView) findViewById(R.id.tv_scenic_name);
        iv_scenic_image = (ImageView) findViewById(R.id.iv_scenic_image);
        iv_scenic_image.setScaleType(ImageView.ScaleType.FIT_XY);
        tv_scenic_content = (TextView) findViewById(R.id.tv_scenic_content);
        rv_other_scenic = (RecyclerView) findViewById(R.id.rv_other_scenic);
        rv_other_scenic.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ScenicActivity.class);
        intent.putExtra("ScenicInfo",scenicInfoList.get(position));
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
