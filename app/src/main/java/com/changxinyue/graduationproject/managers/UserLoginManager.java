package com.changxinyue.graduationproject.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.changxinyue.graduationproject.services.NetworkService;

public class UserLoginManager implements ServiceConnection {
    private Context context;

    private String phone_number;
    private String password;

    public UserLoginManager(Context context) {
        this.context = context;
    }

    public void userLogin(String phone_number, String password){
        this.phone_number = phone_number;
        this.password = password;
        Intent service = new Intent(context, NetworkService.class);
        context.bindService(service,this,Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkService.MyBinder myBinder = (NetworkService.MyBinder) service;
        myBinder.userLogin(context,phone_number,password,this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
