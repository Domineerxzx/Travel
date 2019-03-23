package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.services.NetworkService;

public class ChooseIdentityActivity extends Activity implements View.OnClickListener {

    private Button bt_user_login;
    private Button bt_admin_login;
    private TextView tv_visitor_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_identity);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        Intent networkService = new Intent(this, NetworkService.class);
        startService(networkService);
    }

    private void setOnClickListener() {
        bt_admin_login.setOnClickListener(this);
        bt_user_login.setOnClickListener(this);
        tv_visitor_login.setOnClickListener(this);
    }

    private void initView() {
        bt_user_login = (Button) findViewById(R.id.bt_user_login);
        bt_admin_login = (Button) findViewById(R.id.bt_admin_login);
        tv_visitor_login = (TextView) findViewById(R.id.tv_visitor_login);
        if(getSharedPreferences("userInfo",MODE_PRIVATE).getString("phone_number","").length()!=0){
            Intent user = new Intent(this, MainActivity.class);
            startActivity(user);
            finish();
        }
        if(getSharedPreferences("adminInfo",MODE_PRIVATE).getString("phone_number","").length()!=0){
            Intent admin = new Intent(this, AdminManagerActivity.class);
            startActivity(admin);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_admin_login:
                Intent admin_login = new Intent(this, AdminLoginActivity.class);
                startActivity(admin_login);
                finish();
                break;
            case R.id.bt_user_login:
                Intent user_login = new Intent(this, UserLoginActivity.class);
                startActivity(user_login);
                finish();
                break;
            case R.id.tv_visitor_login:
                Intent visitor = new Intent(this, MainActivity.class);
                startActivity(visitor);
                finish();
                break;
        }
    }
}
