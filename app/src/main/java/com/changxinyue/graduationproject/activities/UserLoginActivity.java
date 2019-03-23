package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.managers.UserLoginManager;

public class UserLoginActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back_to_choose;
    private EditText et_user_phone_number;
    private EditText et_user_password;
    private Button bt_user_login;
    private Button bt_user_create;
    private UserLoginManager userLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        userLoginManager = new UserLoginManager(this);
    }

    private void setOnClickListener() {
        iv_back_to_choose.setOnClickListener(this);
        bt_user_login.setOnClickListener(this);
        bt_user_create.setOnClickListener(this);
    }

    private void initView() {
        iv_back_to_choose = (ImageView) findViewById(R.id.iv_back_to_choose);
        et_user_phone_number = (EditText) findViewById(R.id.et_user_phone_number);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        bt_user_login = (Button) findViewById(R.id.bt_user_login);
        bt_user_create = (Button) findViewById(R.id.bt_user_create);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_to_choose:
                Intent choose = new Intent(this, ChooseIdentityActivity.class);
                startActivity(choose);
                finish();
                break;
            case R.id.bt_user_login:
                String phone_number = et_user_phone_number.getText().toString();
                String password = et_user_password.getText().toString();
                if (phone_number.length() != 0 && password.length() != 0) {
                    userLoginManager.userLogin(phone_number, password);
                } else {
                    Toast.makeText(this, "手机号或密码不能为空！！！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_user_create:
                Intent user_register = new Intent(this, UserRegisterActivity.class);
                startActivity(user_register);
                finish();
                break;
        }
    }
}
