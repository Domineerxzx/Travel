package com.changxinyue.graduationproject.handlers;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;

import com.changxinyue.graduationproject.activities.MainActivity;
import com.changxinyue.graduationproject.activities.UserLoginActivity;
import com.changxinyue.graduationproject.properties.ProjectProperties;

public class UserLoginHandler extends Handler {

    private Context context;
    private ServiceConnection serviceConnection;

    public UserLoginHandler(Context context, ServiceConnection serviceConnection) {
        this.context = context;
        this.serviceConnection = serviceConnection;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case ProjectProperties.USER_LOGIN_SUCCESS:
                context.unbindService(serviceConnection);
                Intent main = new Intent(context, MainActivity.class);
                context.startActivity(main);
                ((UserLoginActivity)context).finish();
                break;
            case ProjectProperties.USER_LOGIN_FAILED:
                context.unbindService(serviceConnection);
                break;
        }
    }
}
