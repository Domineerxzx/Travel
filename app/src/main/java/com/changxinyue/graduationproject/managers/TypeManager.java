package com.changxinyue.graduationproject.managers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TypeManager {

    private Context context;

    public TypeManager(Context context) {
        this.context = context;
    }

    public List<ScenicTypeInfo> getScenicTypeInfoList() {
        List<ScenicTypeInfo> scenicTypeInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor scenicTypeInfoCursor = db.query("scenicTypeInfo", null, null, null, null, null, null);
        if(scenicTypeInfoCursor != null && scenicTypeInfoCursor.getCount() > 0){
            while (scenicTypeInfoCursor.moveToNext()) {
                ScenicTypeInfo typeGeneralizeInfo = new ScenicTypeInfo();
                typeGeneralizeInfo.setType_id(scenicTypeInfoCursor.getInt(0));
                typeGeneralizeInfo.setType_name(scenicTypeInfoCursor.getString(1));
                scenicTypeInfoList.add(typeGeneralizeInfo);
            }
        }
        if (scenicTypeInfoCursor != null) {
            scenicTypeInfoCursor.close();
        }
        db.close();
        return scenicTypeInfoList;
    }

    public List<ScenicInfo> getScenicInfoList(int type_id) {
        List<ScenicInfo> scenicInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor scenicInfoCursor = db.query("scenicInfo", null, "type_id = ?",
                new String[]{String.valueOf(type_id)}, null, null, null);
        if(scenicInfoCursor != null && scenicInfoCursor.getCount() > 0){
            while (scenicInfoCursor.moveToNext()) {
                ScenicInfo scenicInfo = new ScenicInfo();
                scenicInfo.set_id(scenicInfoCursor.getInt(0));
                scenicInfo.setType_id(type_id);
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
