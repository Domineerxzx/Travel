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
import com.changxinyue.graduationproject.managers.UserRegisterManager;

public class UserRegisterActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back_to_user_login;
    private EditText et_user_username;
    private EditText et_user_phone_number;
    private EditText et_user_password;
    private EditText et_user_request_code;
    private Button bt_user_register;
    private Button bt_user_request_code;
    private Button bt_user_login;
    private CheckBox cb_user_agree;
    private String user_phone_number;
    private UserRegisterManager userRegisterManager;
    private String user_password;
    private String user_username;
    private String user_request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back_to_user_login.setOnClickListener(this);
        bt_user_register.setOnClickListener(this);
        bt_user_request_code.setOnClickListener(this);
        bt_user_login.setOnClickListener(this);
    }

    private void initData() {
        userRegisterManager = new UserRegisterManager(this);
    }

    private void initView() {
        iv_back_to_user_login = (ImageView) findViewById(R.id.iv_back_to_user_login);
        et_user_username = (EditText) findViewById(R.id.et_user_username);
        et_user_phone_number = (EditText) findViewById(R.id.et_user_phone_number);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        et_user_request_code = (EditText) findViewById(R.id.et_user_request_code);
        bt_user_register = (Button) findViewById(R.id.bt_user_register);
        bt_user_request_code = (Button) findViewById(R.id.bt_user_request_code);
        bt_user_login = (Button) findViewById(R.id.bt_user_login);
        cb_user_agree = (CheckBox) findViewById(R.id.cb_user_agree);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_to_user_login:
            case R.id.bt_user_login:
                Intent user_login = new Intent(this, UserLoginActivity.class);
                startActivity(user_login);
                finish();
                break;
            case R.id.bt_user_request_code:
                user_phone_number = et_user_phone_number.getText().toString();
                if (user_phone_number.length() == 11) {
                    userRegisterManager.getRequestCode(user_phone_number);
                } else {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_user_register:
                //TODO 用户注册逻辑
                user_phone_number = et_user_phone_number.getText().toString();
                user_password = et_user_password.getText().toString();
                user_username = et_user_username.getText().toString();
                user_request_code = et_user_request_code.getText().toString();
                if (user_username.length() == 0) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (user_phone_number.length() != 11) {
                    Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
                } else if (user_request_code.length() != 4) {
                    Toast.makeText(this, "验证码不能少于4位数", Toast.LENGTH_SHORT).show();
                } else if (user_password.length() == 0) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!cb_user_agree.isChecked()) {
                    Toast.makeText(this, "请查看并同意条款与条件", Toast.LENGTH_SHORT).show();
                } else {
                    userRegisterManager.register(user_phone_number, user_request_code, user_password, user_username);
                }
                break;
        }
    }
}
