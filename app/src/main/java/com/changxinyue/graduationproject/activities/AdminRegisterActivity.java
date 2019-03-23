package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.managers.AdminRegisterManager;

public class AdminRegisterActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back_to_admin_login;
    private EditText et_admin_username;
    private EditText et_admin_phone_number;
    private EditText et_admin_password;
    private EditText et_admin_request_code;
    private Button bt_admin_register;
    private Button bt_admin_request_code;
    private Button bt_admin_login;
    private CheckBox cb_admin_agree;
    private String admin_phone_number;
    private AdminRegisterManager adminRegisterManager;
    private String admin_password;
    private String admin_username;
    private String admin_request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back_to_admin_login.setOnClickListener(this);
        bt_admin_register.setOnClickListener(this);
        bt_admin_request_code.setOnClickListener(this);
        bt_admin_login.setOnClickListener(this);
    }

    private void initData() {
        adminRegisterManager = new AdminRegisterManager(this);
    }

    private void initView() {
        iv_back_to_admin_login = (ImageView) findViewById(R.id.iv_back_to_admin_login);
        et_admin_username = (EditText) findViewById(R.id.et_admin_username);
        et_admin_phone_number = (EditText) findViewById(R.id.et_admin_phone_number);
        et_admin_password = (EditText) findViewById(R.id.et_admin_password);
        et_admin_request_code = (EditText) findViewById(R.id.et_admin_request_code);
        bt_admin_register = (Button) findViewById(R.id.bt_admin_register);
        bt_admin_request_code = (Button) findViewById(R.id.bt_admin_request_code);
        bt_admin_login = (Button) findViewById(R.id.bt_admin_login);
        cb_admin_agree = (CheckBox) findViewById(R.id.cb_admin_agree);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_to_admin_login:
            case R.id.bt_admin_login:
                Intent admin_login = new Intent(this, AdminLoginActivity.class);
                startActivity(admin_login);
                finish();
                break;
            case R.id.bt_admin_request_code:
                admin_phone_number = et_admin_phone_number.getText().toString();
                if (admin_phone_number.length() == 11) {
                    adminRegisterManager.getRequestCode(admin_phone_number);
                } else {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_admin_register:
                //TODO 用户注册逻辑
                admin_phone_number = et_admin_phone_number.getText().toString();
                admin_password = et_admin_password.getText().toString();
                admin_username = et_admin_username.getText().toString();
                admin_request_code = et_admin_request_code.getText().toString();
                if (admin_username.length() == 0) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (admin_phone_number.length() != 11) {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                } else if (admin_request_code.length() != 4) {
                    Toast.makeText(this, "验证码不能少于4位数", Toast.LENGTH_SHORT).show();
                } else if (admin_password.length() == 0) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!cb_admin_agree.isChecked()) {
                    Toast.makeText(this, "请查看并同意条款与条件", Toast.LENGTH_SHORT).show();
                } else {
                    adminRegisterManager.register(admin_phone_number, admin_request_code, admin_password, admin_username);
                }
                break;
        }
    }
}
