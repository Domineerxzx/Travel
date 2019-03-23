package com.changxinyue.graduationproject.utils.intentUtils;

import android.content.Context;
import android.content.Intent;

import com.changxinyue.graduationproject.BuildConfig;

public class PermissionController {

    public static void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
