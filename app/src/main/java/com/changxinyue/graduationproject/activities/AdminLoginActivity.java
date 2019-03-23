package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.managers.AdminLoginManager;

public class AdminLoginActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back_to_choose;
    private EditText et_admin_phone_number;
    private EditText et_admin_password;
    private Button bt_admin_login;
    private Button bt_admin_create;
    private AdminLoginManager adminLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        adminLoginManager = new AdminLoginManager(this);
    }

    private void setOnClickListener() {
        iv_back_to_choose.setOnClickListener(this);
        bt_admin_login.setOnClickListener(this);
        bt_admin_create.setOnClickListener(this);
    }

    private void initView() {
        iv_back_to_choose = (ImageView) findViewById(R.id.iv_back_to_choose);
        et_admin_phone_number = (EditText) findViewById(R.id.et_admin_phone_number);
        et_admin_password = (EditText) findViewById(R.id.et_admin_password);
        bt_admin_login = (Button) findViewById(R.id.bt_admin_login);
        bt_admin_create = (Button) findViewById(R.id.bt_admin_create);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_to_choose:
                Intent choose = new Intent(this, ChooseIdentityActivity.class);
                startActivity(choose);
                finish();
                break;
            case R.id.bt_admin_login:
                String phone_number = et_admin_phone_number.getText().toString();
                String password = et_admin_password.getText().toString();
                if (phone_number.length() != 0 && password.length() != 0) {
                    adminLoginManager.adminLogin(phone_number, password);
                } else {
                    Toast.makeText(this, "手机号或密码不能为空！！！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_admin_create:
                Intent admin_register = new Intent(this, AdminRegisterActivity.class);
                startActivity(admin_register);
                finish();
                break;
        }
    }
}
