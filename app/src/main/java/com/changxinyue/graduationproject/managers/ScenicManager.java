package com.changxinyue.graduationproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ScenicManager {

    private Context context;

    public ScenicManager(Context context) {
        this.context = context;
    }

    public List<ScenicInfo> getScenicInfoList(String city_name) {
        List<ScenicInfo> scenicInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor scenicInfoCursor = db.query("scenicInfo", null, "city_name = ?",
                new String[]{String.valueOf(city_name)}, null, null, null);
        if(scenicInfoCursor != null && scenicInfoCursor.getCount() > 0){
            while (scenicInfoCursor.moveToNext()) {
                ScenicInfo scenicInfo = new ScenicInfo();
                scenicInfo.set_id(scenicInfoCursor.getInt(0));
                scenicInfo.setType_id(scenicInfoCursor.getInt(1));
                scenicInfo.setCity_name(scenicInfoCursor.getString(2));
                scenicInfo.setScenic_content(scenicInfoCursor.getString(3));
                scenicInfo.setScenic_image(scenicInfoCursor.getString(4));
                scenicInfo.setScenic_name(scenicInfoCursor.getString(5));
                scenicInfoList.add(scenicInfo);
            }
        }
        if (scenicInfoCursor != null) {
            scenicInfoCursor.close();
        }
        db.close();
        return scenicInfoList;
    }
}
