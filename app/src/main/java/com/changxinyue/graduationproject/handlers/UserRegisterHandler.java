package com.changxinyue.graduationproject.handlers;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;

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
                break;
            case ProjectProperties.GET_REQUEST_CODE_FAILED:
                break;
        }
    }
}
