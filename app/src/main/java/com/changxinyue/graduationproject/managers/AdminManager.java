package com.changxinyue.graduationproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.changxinyue.graduationproject.activities.ChangeScenicTypeActivity;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminManager {

    private Context context;

    public AdminManager(Context context) {
        this.context = context;
    }

    public List<ScenicTypeInfo> getScenicTypeInfoList() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<ScenicTypeInfo> scenicTypeInfoList = new ArrayList<>();
        Cursor scenicTypeInfoCursor = db.query("scenicTypeInfo", null, null, null, null, null, null);
        if (scenicTypeInfoCursor != null && scenicTypeInfoCursor.getCount() > 0) {
            while (scenicTypeInfoCursor.moveToNext()) {
                ScenicTypeInfo scenicTypeInfo = new ScenicTypeInfo();
                scenicTypeInfo.setType_id(scenicTypeInfoCursor.getInt(0));
                scenicTypeInfo.setType_name(scenicTypeInfoCursor.getString(1));
                scenicTypeInfoList.add(scenicTypeInfo);
            }
        }
        if (scenicTypeInfoCursor != null) {
            scenicTypeInfoCursor.close();
        }
        db.close();
        return scenicTypeInfoList;
    }

    public void saveScenicTypeChange(List<ScenicTypeInfo> scenicTypeInfoList) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.delete("scenicTypeInfo", null, null);
        for (ScenicTypeInfo scenicTypeInfo : scenicTypeInfoList) {
            ContentValues contentValues = new ContentValues();
            if(scenicTypeInfo.getType_name().length() == 0){
                Toast.makeText(context, "分类名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            contentValues.put("type_name", scenicTypeInfo.getType_name());
            db.insert("scenicTypeInfo", null, contentValues);
        }
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        db.close();
        ((ChangeScenicTypeActivity) context).finish();
    }

    public List<CityInfo> getCityInfoList() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<CityInfo> cityInfoList = new ArrayList<>();
        Cursor cityInfoCursor = db.query("cityInfo", null, null, null, null, null, null);
        if (cityInfoCursor != null && cityInfoCursor.getCount() > 0) {
            while (cityInfoCursor.moveToNext()) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.set_id(cityInfoCursor.getInt(0));
                cityInfo.setCity_name(cityInfoCursor.getString(1));
                cityInfo.setCity_name_spell(cityInfoCursor.getString(2));
                cityInfo.setCity_score(cityInfoCursor.getFloat(3));
                cityInfo.setCity_image(cityInfoCursor.getString(4));
                cityInfo.setCity_content(cityInfoCursor.getString(5));
                cityInfoList.add(cityInfo);
            }
        }
        if (cityInfoCursor != null) {
            cityInfoCursor.close();
        }
        db.close();
        return cityInfoList;
    }

    public void addScenicInfo(ContentValues scenicInfo) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.insert("scenicInfo", null, scenicInfo);
        db.close();
    }

    public List<ScenicInfo> getScenicInfoList() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<ScenicInfo> scenicInfoList = new ArrayList<>();
        Cursor scenicInfoCursor = db.query("scenicInfo", null, null, null, null, null, null);
        if (scenicInfoCursor != null && scenicInfoCursor.getCount() > 0) {
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

    public void deleteScenic(int scenic_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.delete("scenicInfo", "_id = ?", new String[]{String.valueOf(scenic_id)});
    }

    public ScenicTypeInfo getChooseScenicType(int type_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ScenicTypeInfo scenicTypeInfo = new ScenicTypeInfo();
        Cursor scenicTypeInfoCursor = db.query("scenicTypeInfo", null, "type_id = ?", new String[]{String.valueOf(type_id)}, null, null, null);
        if (scenicTypeInfoCursor != null && scenicTypeInfoCursor.getCount() > 0){
            scenicTypeInfoCursor.moveToNext();
            scenicTypeInfo.setType_id(scenicTypeInfoCursor.getInt(0));
            scenicTypeInfo.setType_name(scenicTypeInfoCursor.getString(1));
        }
        if (scenicTypeInfoCursor != null) {
            scenicTypeInfoCursor.close();
        }
        db.close();
        return scenicTypeInfo;
    }
}
