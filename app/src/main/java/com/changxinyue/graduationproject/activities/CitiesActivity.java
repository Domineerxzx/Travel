package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.RecommendTravelNotesAdapter;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.TravelNoteInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.CityManager;
import com.changxinyue.graduationproject.managers.FirstPageManager;
import com.changxinyue.graduationproject.utils.BlurTransformation;
import java.util.List;


public class CitiesActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private RecyclerView rv_recommend_travel_notes;
    private ImageView iv_back;
    private CityInfo cityInfo;
    private ImageView iv_city;
    private CityManager cityManager;
    private List<TravelNoteInfo> travelNoteInfoList;
    private RecommendTravelNotesAdapter travelNotesAdapter;
    private TextView tv_city_name;
    private TextView tv_city_name_speak;
    private TextView tv_city_score;
    private ImageView iv_collection;
    private FirstPageManager firstPageManager;
    private SharedPreferences userInfo;
    private String phone_number;
    private boolean isCollection;
    private TextView tv_city_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
        iv_collection.setOnClickListener(this);
    }

    private void initData() {
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        cityManager = new CityManager(this);
        Intent intent = getIntent();
        cityInfo = (CityInfo) intent.getSerializableExtra("CityInfo");
        cityInfo = cityManager.getCityInfo(cityInfo.getCity_name());
        tv_city_content.setText("        "+cityInfo.getCity_content());
        String city_image = cityInfo.getCity_image();
        Glide.with(this).load(city_image).apply(RequestOptions.bitmapTransform(new BlurTransformation(15,1))).into(iv_city);
        travelNoteInfoList = cityManager.getTravelNoteInfoList(cityInfo.getCity_name());
        travelNotesAdapter = new RecommendTravelNotesAdapter(this, travelNoteInfoList);
        rv_recommend_travel_notes.setAdapter(travelNotesAdapter);
        travelNotesAdapter.setOnItemClickListener(this);
        tv_city_name.setText(cityInfo.getCity_name());
        tv_city_name_speak.setText(cityInfo.getCity_name_spell());
        tv_city_score.setText(String.valueOf(cityInfo.getCity_score()));
        isCollection = cityManager.getIsCollection(cityInfo.get_id());
        if(isCollection){
            iv_collection.setBackgroundResource(R.mipmap.collection_click);
        }
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_recommend_travel_notes = findViewById(R.id.rv_recommend_travel_notes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend_travel_notes.setLayoutManager(gridLayoutManager);
        iv_city = (ImageView) findViewById(R.id.iv_city);
        tv_city_name = (TextView) findViewById(R.id.tv_city_name);
        tv_city_name_speak = (TextView) findViewById(R.id.tv_city_name_speak);
        tv_city_score = (TextView) findViewById(R.id.tv_city_score);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        tv_city_content = (TextView) findViewById(R.id.tv_city_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collection:
                if (phone_number.length() > 0) {
                    if (isCollection) {
                        boolean deleteCommodityCollection = cityManager.deleteCityCollection(cityInfo.get_id());
                        if (deleteCommodityCollection) {
                            iv_collection.setBackgroundResource(R.mipmap.collection_unclick);
                            isCollection = false;
                        }
                    } else {
                        boolean addCommodityCollection = cityManager.addCityCollection(cityInfo.get_id());
                        if (addCommodityCollection) {
                            iv_collection.setBackgroundResource(R.mipmap.collection_click);
                            isCollection = true;
                        }
                    }
                } else {
                    Toast.makeText(this, "还没登录呢，不能收藏城市", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, TravelNoteActivity.class);
        intent.putExtra("TravelNoteInfo",travelNoteInfoList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
