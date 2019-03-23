package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.ScenicTypeAdapter;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;
import com.changxinyue.graduationproject.managers.AdminManager;
import com.changxinyue.graduationproject.views.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ChangeScenicTypeActivity extends Activity implements View.OnClickListener {

    private MyListView lv_scenic_type;
    private ScenicTypeAdapter scenicTypeAdapter;
    private AdminManager adminManager;
    private List<ScenicTypeInfo> scenicTypeInfoList;
    private ImageView iv_close_change_scenic_type_info;
    private ImageView iv_add_scenic_type;
    private ImageView iv_delete_scenic_type;
    private Button bt_change_scenic_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_scenic_type);
        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        lv_scenic_type = (MyListView) findViewById(R.id.lv_scenic_type);
        iv_close_change_scenic_type_info = (ImageView) findViewById(R.id.iv_close_change_scenic_type_info);
        iv_add_scenic_type = (ImageView) findViewById(R.id.iv_add_scenic_type);
        iv_delete_scenic_type = (ImageView) findViewById(R.id.iv_delete_scenic_type);
        bt_change_scenic_type = (Button) findViewById(R.id.bt_change_scenic_type);
    }

    private void initData() {
        adminManager = new AdminManager(this);
        scenicTypeInfoList = adminManager.getScenicTypeInfoList();
        scenicTypeAdapter = new ScenicTypeAdapter(this,scenicTypeInfoList);
        lv_scenic_type.setAdapter(scenicTypeAdapter);
    }

    private void setOnClickListener() {
        iv_close_change_scenic_type_info.setOnClickListener(this);
        iv_add_scenic_type.setOnClickListener(this);
        iv_delete_scenic_type.setOnClickListener(this);
        bt_change_scenic_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_change_scenic_type_info:
                finish();
                break;
            case R.id.iv_add_scenic_type:
                ScenicTypeInfo scenicTypeInfo = new ScenicTypeInfo();
                scenicTypeInfoList = scenicTypeAdapter.getScenicTypeInfoList();
                scenicTypeInfoList.add(scenicTypeInfo);
                scenicTypeAdapter.setScenicTypeInfoList(scenicTypeInfoList);
                break;
            case R.id.iv_delete_scenic_type:
                scenicTypeInfoList.remove(scenicTypeInfoList.size()-1);
                scenicTypeAdapter.setScenicTypeInfoList(scenicTypeInfoList);
                break;
            case R.id.bt_change_scenic_type:
                scenicTypeInfoList = scenicTypeAdapter.getScenicTypeInfoList();
                adminManager.saveScenicTypeChange(scenicTypeInfoList);
                break;
        }
    }
}
