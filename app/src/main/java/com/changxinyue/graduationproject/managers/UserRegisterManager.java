package com.changxinyue.graduationproject.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.changxinyue.graduationproject.services.NetworkService;

public class UserRegisterManager implements ServiceConnection {

    private Context context;
    private String user_phone_number;
    private String request_code;
    private String user_password;
    private String user_nickname;

    private int registerState;

    private static final int STATE_GET_REQUEST = 0;
    private static final int STATE_REGISTER = 1;

    public UserRegisterManager(Context context) {
        this.context = context;
    }

    public void getRequestCode(String user_phone_number) {
        this.user_phone_number = user_phone_number;
        Intent intent = new Intent(context, NetworkService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        registerState = STATE_GET_REQUEST;
    }

    public void register(String user_phone_number, String request_code, String user_password, String user_nickname) {
        this.user_phone_number = user_phone_number;
        this.request_code = request_code;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        Intent intent = new Intent(context, NetworkService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        registerState = STATE_REGISTER;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkService.MyBinder myBinder = (NetworkService.MyBinder) service;
        switch (registerState) {
            case STATE_GET_REQUEST:
                myBinder.userGetRequestCode(context, user_phone_number, this);
                break;
            case STATE_REGISTER:
                myBinder.userRegister(context, user_phone_number, request_code, user_password, user_nickname, this);
                break;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
