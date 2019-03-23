package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.RecommendCitiesAdapter;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.CollectionManager;

import java.util.List;

public class CollectionActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private ImageView iv_close_collection;
    private RecyclerView rv_collection;
    private CollectionManager collectionManager;
    private SharedPreferences userInfo;
    private String phone_number;
    private List<CityInfo> cityInfoList;
    private RecommendCitiesAdapter recommendCitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        initData();
        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cityInfoList = collectionManager.getCollectionInfoList(phone_number);
        recommendCitiesAdapter.setCityInfoList(cityInfoList);
    }

    private void initView() {
        iv_close_collection = (ImageView) findViewById(R.id.iv_close_collection);
        rv_collection = (RecyclerView) findViewById(R.id.rv_collection);
    }

    private void initData() {
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        collectionManager = new CollectionManager(this);
        cityInfoList = collectionManager.getCollectionInfoList(phone_number);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_collection.setLayoutManager(gridLayoutManager);
        recommendCitiesAdapter = new RecommendCitiesAdapter(this, cityInfoList);
        rv_collection.setAdapter(recommendCitiesAdapter);
        recommendCitiesAdapter.setOnItemClickListener(this);
    }

    private void setOnClickListener() {
        iv_close_collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_collection:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, CitiesActivity.class);
        intent.putExtra("CityInfo", cityInfoList.get(position));
       startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
