package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.TravelNoteInfo;

import java.io.Serializable;

public class TravelNoteActivity extends Activity implements View.OnClickListener {

    private ImageView iv_image;
    private TextView tv_content;
    private TextView tv_title;
    private ImageView iv_back;
    private TravelNoteInfo travelNoteInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_note);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        travelNoteInfo = (TravelNoteInfo) intent.getSerializableExtra("TravelNoteInfo");
        tv_title.setText(travelNoteInfo.getTitle());
        tv_content.setText(travelNoteInfo.getContent());
        Glide.with(this).load(travelNoteInfo.getImage()).into(iv_image);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
