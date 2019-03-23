package com.changxinyue.graduationproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxinyue.graduationproject.activities.CitiesActivity;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.TravelNoteInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CityManager {

    private Context context;
    private SharedPreferences userInfo;
    private String phone_number;

    public CityManager(Context context) {
        this.context = context;
    }

    public List<TravelNoteInfo> getTravelNoteInfoList(String city_name) {
        List<TravelNoteInfo> travelNoteInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor cityRecommendInfoCursor = db.query("travelNoteInfo", null, "city_name = ?", new String[]{city_name}, null, null, null);
        if (cityRecommendInfoCursor != null && cityRecommendInfoCursor.getCount() > 0) {
            while (cityRecommendInfoCursor.moveToNext()) {
                TravelNoteInfo travelNoteInfo = new TravelNoteInfo();
                travelNoteInfo.set_id(cityRecommendInfoCursor.getInt(0));
                travelNoteInfo.setCity_name(cityRecommendInfoCursor.getString(1));
                travelNoteInfo.setTitle(cityRecommendInfoCursor.getString(2));
                travelNoteInfo.setContent(cityRecommendInfoCursor.getString(3));
                travelNoteInfo.setImage(cityRecommendInfoCursor.getString(4));
                travelNoteInfoList.add(travelNoteInfo);
            }
        }
        return travelNoteInfoList;
    }

    public CityInfo getCityInfo(String city_name) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor cityInfoCursor = db.query("cityInfo", null, "city_name = ?", new String[]{city_name}, null, null, null);
        CityInfo cityInfo = new CityInfo();
        if (cityInfoCursor != null && cityInfoCursor.getCount() > 0) {
            while (cityInfoCursor.moveToNext()) {
                cityInfo.set_id(cityInfoCursor.getInt(0));
                cityInfo.setCity_name(cityInfoCursor.getString(1));
                cityInfo.setCity_name_spell(cityInfoCursor.getString(2));
                cityInfo.setCity_score(cityInfoCursor.getFloat(3));
                cityInfo.setCity_image(cityInfoCursor.getString(4));
                cityInfo.setCity_content(cityInfoCursor.getString(5));
            }
        }
        return cityInfo;
    }

    public boolean deleteCityCollection(int id) {
        userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        int cityCollectionInfo = db.delete("cityCollectionInfo", "city_id = ? and phone_number = ?", new String[]{String.valueOf(id),phone_number});
        if (cityCollectionInfo >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addCityCollection(int id) {
        userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_id", id);
        contentValues.put("phone_number",phone_number);
        long cityCollectionInfo = db.insert("cityCollectionInfo", null, contentValues);
        if (cityCollectionInfo >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsCollection(int id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor cityCollectionInfo = db.query("cityCollectionInfo", null, "city_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cityCollectionInfo != null && cityCollectionInfo.getCount() > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }
}
