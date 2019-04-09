package com.changxinyue.graduationproject.managers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.TravelNoteInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FirstPageManager {

    private Context context;

    public FirstPageManager(Context context) {
        this.context = context;
    }

    public List<String> getBannerImageList() {
        List<String> bannerImageList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityRecommendInfo = db.query("cityRecommendInfo",
                new String[]{"city_image"}, null, null,
                null, null, null);
        if (commodityRecommendInfo != null && commodityRecommendInfo.getCount() > 0) {
            while (commodityRecommendInfo.moveToNext()) {
                String recommendImage = commodityRecommendInfo.getString(0);
                bannerImageList.add(recommendImage);
                if (bannerImageList.size() == 5) {
                    break;
                }
            }
        }
        if (commodityRecommendInfo != null) {
            commodityRecommendInfo.close();
        }
        db.close();
        return bannerImageList;
    }

    public List<CityInfo> getCityRecommendInfoList(){
        List<CityInfo> cityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor cityRecommendInfoCursor = db.query("cityRecommendInfo", null, null, null, null, null, null);
        if(cityRecommendInfoCursor != null && cityRecommendInfoCursor.getCount() > 0){
            while (cityRecommendInfoCursor.moveToNext()) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.set_id(cityRecommendInfoCursor.getInt(0));
                cityInfo.setCity_name(cityRecommendInfoCursor.getString(1));
                cityInfo.setCity_image(cityRecommendInfoCursor.getString(2));
                cityInfo.setCity_content(cityRecommendInfoCursor.getString(3));
                cityInfoList.add(cityInfo);
            }
        }
        return cityInfoList;
    }

    public List<TravelNoteInfo> getTravelNoteInfoList(){
        List<TravelNoteInfo> travelNoteInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor travelNoteInfoCursor = db.query("travelNoteInfo", null, null, null, null, null, null);
        if(travelNoteInfoCursor != null && travelNoteInfoCursor.getCount() > 0){
            while (travelNoteInfoCursor.moveToNext()) {
                TravelNoteInfo travelNoteInfo = new TravelNoteInfo();
                travelNoteInfo.set_id(travelNoteInfoCursor.getInt(0));
                travelNoteInfo.setCity_name(travelNoteInfoCursor.getString(1));
                travelNoteInfo.setTitle(travelNoteInfoCursor.getString(2));
                travelNoteInfo.setContent(travelNoteInfoCursor.getString(3));
                travelNoteInfo.setImage(travelNoteInfoCursor.getString(4));
                travelNoteInfoList.add(travelNoteInfo);
            }
        }
        return travelNoteInfoList;
    }
}
