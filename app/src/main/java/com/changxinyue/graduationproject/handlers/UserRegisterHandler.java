package com.changxinyue.graduationproject.handlers;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;

import com.changxinyue.graduationproject.activities.AdminManagerActivity;
import com.changxinyue.graduationproject.activities.AdminRegisterActivity;
import com.changxinyue.graduationproject.activities.MainActivity;
import com.changxinyue.graduationproject.activities.UserLoginActivity;
import com.changxinyue.graduationproject.activities.UserRegisterActivity;
import com.changxinyue.graduationproject.properties.ProjectProperties;

public class UserRegisterHandler extends Handler {

    private Context context;
    private ServiceConnection serviceConnection;

    public UserRegisterHandler(Context context, ServiceConnection serviceConnection) {
        this.context = context;
        this.serviceConnection = serviceConnection;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case ProjectProperties.GET_REQUEST_CODE_SUCCESS:
                context.unbindService(serviceConnection);
                break;
            case ProjectProperties.GET_REQUEST_CODE_FAILED:
                context.unbindService(serviceConnection);
                break;
            case ProjectProperties.REGISTER_SUCCESS:
                context.unbindService(serviceConnection);
                Intent main = new Intent(context, MainActivity.class);
                context.startActivity(main);
                ((UserRegisterActivity)context).finish();
                break;
            case ProjectProperties.REGISTER_FAILED:
                context.unbindService(serviceConnection);
                break;
        }
    }
}
