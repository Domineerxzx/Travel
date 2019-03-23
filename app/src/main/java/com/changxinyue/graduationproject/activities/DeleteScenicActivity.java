package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.TypeContentAdapter;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.AdminManager;

import java.util.List;

public class DeleteScenicActivity extends Activity implements View.OnClickListener,OnItemClickListener {

    private ImageView iv_close_delete_scenic;
    private AdminManager adminManager;
    private RecyclerView rv_scenic_info;
    private SharedPreferences adminInfo;
    private String phone_number;
    private TypeContentAdapter typeContentAdapter;
    private List<ScenicInfo> scenicInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_scenic);

        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_delete_scenic = (ImageView) findViewById(R.id.iv_close_delete_scenic);
        rv_scenic_info = (RecyclerView) findViewById(R.id.rv_scenic_info);
    }

    private void initData() {
        adminManager = new AdminManager(this);
        scenicInfoList = adminManager.getScenicInfoList();
        rv_scenic_info.setLayoutManager(new GridLayoutManager(this,4));
        typeContentAdapter = new TypeContentAdapter(this, scenicInfoList);
        rv_scenic_info.setAdapter(typeContentAdapter);
    }

    private void setOnClickListener() {
        iv_close_delete_scenic.setOnClickListener(this);
        typeContentAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_delete_scenic:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        adminManager.deleteScenic(scenicInfoList.get(position).get_id());
        scenicInfoList.remove(position);
        typeContentAdapter.setScenicInfoList(scenicInfoList);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
