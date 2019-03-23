package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changxinyue.graduationproject.R;

public class AdminManagerActivity extends Activity implements View.OnClickListener {

    private TextView tv_admin;
    private LinearLayout ll_add_scenic;
    private LinearLayout ll_browse_and_change_scenic_info;
    private LinearLayout ll_delete_scenic;
    private LinearLayout ll_change_scenic_type;
    private TextView tv_add_scenic;
    private TextView tv_browse_and_change_scenic_info;
    private TextView tv_delete_scenic;
    private TextView tv_change_scenic_type;
    private ImageView iv_add_scenic;
    private ImageView iv_browse_and_change_scenic_info;
    private ImageView iv_delete_scenic;
    private ImageView iv_change_scenic_type;
    private SharedPreferences adminInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
    }

    private void setOnClickListener() {
        tv_admin.setOnClickListener(this);
        ll_add_scenic.setOnClickListener(this);
        ll_browse_and_change_scenic_info.setOnClickListener(this);
        ll_delete_scenic.setOnClickListener(this);
        ll_change_scenic_type.setOnClickListener(this);
        tv_add_scenic.setOnClickListener(this);
        tv_browse_and_change_scenic_info.setOnClickListener(this);
        tv_delete_scenic.setOnClickListener(this);
        tv_change_scenic_type.setOnClickListener(this);
        iv_add_scenic.setOnClickListener(this);
        iv_browse_and_change_scenic_info.setOnClickListener(this);
        iv_delete_scenic.setOnClickListener(this);
        iv_change_scenic_type.setOnClickListener(this);
    }

    private void initView() {
        tv_admin = (TextView) findViewById(R.id.tv_admin);
        ll_add_scenic = (LinearLayout) findViewById(R.id.ll_add_scenic);
        ll_browse_and_change_scenic_info = (LinearLayout) findViewById(R.id.ll_browse_and_change_scenic_info);
        ll_delete_scenic = (LinearLayout) findViewById(R.id.ll_delete_scenic);
        ll_change_scenic_type = (LinearLayout) findViewById(R.id.ll_change_scenic_type);
        tv_add_scenic = (TextView) findViewById(R.id.tv_add_scenic);
        tv_browse_and_change_scenic_info = (TextView) findViewById(R.id.tv_browse_and_change_scenic_info);
        tv_delete_scenic = (TextView) findViewById(R.id.tv_delete_scenic);
        tv_change_scenic_type = (TextView) findViewById(R.id.tv_change_scenic_type);
        iv_add_scenic = (ImageView) findViewById(R.id.iv_add_scenic);
        iv_browse_and_change_scenic_info = (ImageView) findViewById(R.id.iv_browse_and_change_scenic_info);
        iv_delete_scenic = (ImageView) findViewById(R.id.iv_delete_scenic);
        iv_change_scenic_type = (ImageView) findViewById(R.id.iv_change_scenic_type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_admin:
                SharedPreferences.Editor edit = adminInfo.edit();
                edit.clear();
                edit.commit();
                Intent intent = new Intent(this, ChooseIdentityActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_add_scenic:
            case R.id.tv_add_scenic:
            case R.id.iv_add_scenic:
                Intent addCommodity = new Intent(this, AddScenicActivity.class);
                startActivity(addCommodity);
                break;
            case R.id.ll_browse_and_change_scenic_info:
            case R.id.tv_browse_and_change_scenic_info:
            case R.id.iv_browse_and_change_scenic_info:
                Intent browseCommodity = new Intent(this, BrowseScenicInfoActivity.class);
                startActivity(browseCommodity);
                break;
            case R.id.ll_delete_scenic:
            case R.id.tv_delete_scenic:
            case R.id.iv_delete_scenic:
                Intent deleteCommodity = new Intent(this, DeleteScenicActivity.class);
                startActivity(deleteCommodity);
                break;
            case R.id.ll_change_scenic_type:
            case R.id.tv_change_scenic_type:
            case R.id.iv_change_scenic_type:
                Intent changeScenicTypeActivity = new Intent(this, ChangeScenicTypeActivity.class);
                startActivity(changeScenicTypeActivity);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
