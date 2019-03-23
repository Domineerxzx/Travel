package com.changxinyue.graduationproject.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.changxinyue.graduationproject.services.NetworkService;

public class AdminRegisterManager implements ServiceConnection {
    private Context context;
    private String admin_phone_number;
    private String request_code;
    private String admin_password;
    private String admin_nickname;

    private int registerState;

    private static final int STATE_GET_REQUEST = 0;
    private static final int STATE_REGISTER = 1;

    public AdminRegisterManager(Context context) {
        this.context = context;
    }

    public void getRequestCode(String admin_phone_number) {
        this.admin_phone_number = admin_phone_number;
        Intent intent = new Intent(context, NetworkService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        registerState = STATE_GET_REQUEST;
    }

    public void register(String admin_phone_number, String request_code, String admin_password, String admin_nickname) {
        this.admin_phone_number = admin_phone_number;
        this.request_code = request_code;
        this.admin_password = admin_password;
        this.admin_nickname = admin_nickname;
        Intent intent = new Intent(context, NetworkService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        registerState = STATE_REGISTER;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkService.MyBinder myBinder = (NetworkService.MyBinder) service;
        switch (registerState) {
            case STATE_GET_REQUEST:
                myBinder.adminGetRequestCode(context, admin_phone_number, this);
                break;
            case STATE_REGISTER:
                myBinder.adminRegister(context, admin_phone_number, request_code, admin_password, admin_nickname, this);
                break;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
