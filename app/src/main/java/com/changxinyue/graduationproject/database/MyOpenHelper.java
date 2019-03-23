package com.changxinyue.graduationproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(@Nullable Context context) {
        super(context, "travel", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户/管理员
        db.execSQL("create table userInfo(phone_number varchar(20) primary key unique,password varchar(20),nickname varchar(20),user_head varchar(200))");
        db.execSQL("create table adminInfo(phone_number varchar(20) primary key unique,password varchar(20),nickname varchar(20),user_head varchar(200))");

        //城市信息
        db.execSQL("create table cityInfo(_id Integer primary key autoincrement,city_name varchar(20) unique,city_name_spell varchar(50),city_score float,city_image varchar(200),city_content varchar(200))");

        //游记信息
        db.execSQL("create table travelNoteInfo(_id Integer primary key autoincrement,city_name varchar(20),title varchar(30),content varchar(5000),image varchar(200)," +
                "FOREIGN KEY (city_name) REFERENCES cityInfo(city_name))");

        //景区类别
        db.execSQL("create table scenicTypeInfo(type_id Integer primary key autoincrement,type_name varchar(20))");

        //景区信息
        db.execSQL("create table scenicInfo(_id Integer primary key autoincrement,type_id Integer,city_name varchar(20),scenic_content varchar(1000),scenic_image varchar(200),scenic_name varchar(50)," +
                "FOREIGN KEY (type_id) REFERENCES scenicTypeInfo(type_id)," +
                "FOREIGN KEY (city_name) REFERENCES cityInfo(city_name))");

        //发布信息
        db.execSQL("create table submitInfo(submit_id Integer primary key autoincrement,phone_number varchar(20),nickname varchar(20),user_head varchar(200)," +
                "submit_content varchar(500),FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");
        //发现图片表
        db.execSQL("create table submitImageInfo(_id Integer primary key autoincrement,submit_id number,submit_image varchar(200)," +
                "FOREIGN KEY (submit_id) REFERENCES submitInfo(submit_id))");
        //城市推荐表
        db.execSQL("create table cityRecommendInfo(_id Integer primary key autoincrement,city_name varcher(20),city_image varchar(200),city_content varchar(200)," +
                "FOREIGN KEY (city_name) REFERENCES cityInfo(city_name))");

        //城市收藏表
        db.execSQL("create table cityCollectionInfo(_id Integer primary key autoincrement,city_id Integer,phone_number varchar(20)," +
                "FOREIGN KEY (city_id) REFERENCES cityInfo(_id)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
