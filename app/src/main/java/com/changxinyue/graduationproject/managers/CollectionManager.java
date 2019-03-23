package com.changxinyue.graduationproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.changxinyue.graduationproject.activities.CollectionActivity;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CollectionManager {

    private Context context;

    public CollectionManager(Context context) {
        this.context = context;
    }

    public List<CityInfo> getCollectionInfoList(String phone_number) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<Integer> cityIdList = new ArrayList<>();
        Cursor cityCollectionInfo = db.query("cityCollectionInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (cityCollectionInfo != null && cityCollectionInfo.getCount() > 0) {
            while (cityCollectionInfo.moveToNext()) {
                int city_id = cityCollectionInfo.getInt(1);
                cityIdList.add(city_id);
            }
        }
        if (cityCollectionInfo != null) {
            cityCollectionInfo.close();
        }
        List<CityInfo> cityInfoList = new ArrayList<>();
        for (int city_id : cityIdList) {
            Cursor cityInfoCursor = db.query("cityInfo", null, "_id = ?", new String[]{String.valueOf(city_id)}, null, null, null);
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
        }
        db.close();
        return cityInfoList;
    }
}
