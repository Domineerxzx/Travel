package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.SubmitAdapter;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.AdminManager;
import com.changxinyue.graduationproject.properties.ProjectProperties;
import com.changxinyue.graduationproject.utils.RealPathFromUriUtils;
import com.changxinyue.graduationproject.utils.dialogUtils.ChooseUserHeadDialogUtil;
import com.changxinyue.graduationproject.utils.dialogUtils.SingleChooseDialog;

import java.io.File;
import java.util.List;

public class AddScenicActivity extends Activity implements View.OnClickListener{

    private EditText et_scenic_name;
    private EditText et_scenic_content;
    private TextView tv_scenic_type_content;
    private ImageView iv_scenic_type;
    private TextView tv_scenic_city_content;
    private ImageView iv_scenic_city;
    private ImageView iv_scenic_image_show;
    private Button bt_add_scenic;
    private AdminManager adminManager;
    private SharedPreferences adminInfo;
    private String phone_number;
    private long timeStamp;
    private ImageView iv_delete_scenic_image_show;
    private String image_show;
    private ScenicTypeInfo chooseScenicTypeInfo;
    private ImageView iv_close_add_commodity;
    private CityInfo chooseCityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scenic);

        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        adminManager = new AdminManager(this);
        Glide.with(this).load(R.drawable.submit).into(iv_scenic_image_show);
        image_show = "";
        chooseScenicTypeInfo = new ScenicTypeInfo();
        chooseCityInfo = new CityInfo();
    }

    private void setOnClickListener() {
        iv_close_add_commodity.setOnClickListener(this);
        tv_scenic_type_content.setOnClickListener(this);
        iv_scenic_type.setOnClickListener(this);
        tv_scenic_city_content.setOnClickListener(this);
        iv_scenic_city.setOnClickListener(this);
        iv_scenic_image_show.setOnClickListener(this);
        iv_delete_scenic_image_show.setOnClickListener(this);
        bt_add_scenic.setOnClickListener(this);
    }

    private void initView() {
        iv_close_add_commodity = findViewById(R.id.iv_close_add_scenic);
        et_scenic_name = findViewById(R.id.et_scenic_name);
        et_scenic_content = findViewById(R.id.et_scenic_content);
        tv_scenic_type_content = findViewById(R.id.tv_scenic_type_content);
        iv_scenic_type = findViewById(R.id.iv_scenic_type);
        tv_scenic_city_content = findViewById(R.id.tv_scenic_city_content);
        iv_scenic_city = findViewById(R.id.iv_scenic_city);
        iv_scenic_image_show = findViewById(R.id.iv_scenic_image_show);
        iv_delete_scenic_image_show = findViewById(R.id.iv_delete_scenic_image_show);
        iv_delete_scenic_image_show.setVisibility(View.GONE);
        iv_delete_scenic_image_show.bringToFront();
        iv_scenic_image_show.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bt_add_scenic = findViewById(R.id.bt_add_scenic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_add_scenic:
                finish();
                break;
            case R.id.tv_scenic_type_content:
            case R.id.iv_scenic_type:
                final List<ScenicTypeInfo> scenicTypeInfoList = adminManager.getScenicTypeInfoList();
                final String[] chooseScenicType = new String[scenicTypeInfoList.size()];
                int i = 0;
                for (ScenicTypeInfo scenicTypeInfo : scenicTypeInfoList) {
                    chooseScenicType[i] = scenicTypeInfo.getType_name();
                    i++;
                }
                SingleChooseDialog chooseScenicTypeDialog = new SingleChooseDialog();
                chooseScenicTypeDialog.show("请选择景区类别", chooseScenicType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_scenic_type_content.setText(chooseScenicType[which]);
                        chooseScenicTypeInfo = scenicTypeInfoList.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.tv_scenic_city_content:
            case R.id.iv_scenic_city:
                final List<CityInfo> cityInfoList = adminManager.getCityInfoList();
                final String[] chooseCityType = new String[cityInfoList.size()];
                int j = 0;
                for (CityInfo cityInfo : cityInfoList) {
                    chooseCityType[j] = cityInfo.getCity_name();
                    j++;
                }
                SingleChooseDialog chooseCityDialog = new SingleChooseDialog();
                chooseCityDialog.show("请选择城市", chooseCityType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_scenic_type_content.setText(chooseCityType[which]);
                        chooseCityInfo = cityInfoList.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.iv_scenic_image_show:
                adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
                phone_number = adminInfo.getString("phone_number", "");
                timeStamp = System.currentTimeMillis();
                ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, null, phone_number, timeStamp);
                break;
            case R.id.iv_delete_scenic_image_show:
                Glide.with(this).load(R.drawable.submit).into(iv_scenic_image_show);
                image_show = "";
                iv_delete_scenic_image_show.setVisibility(View.GONE);
                break;
            case R.id.bt_add_scenic:
                String scenic_name = et_scenic_name.getText().toString().trim();
                String scenic_content = et_scenic_content.getText().toString().trim();
                int scenicTypeId = chooseScenicTypeInfo.getType_id();
                String city_name = chooseCityInfo.getCity_name();
                ContentValues scenicInfo = new ContentValues();
                scenicInfo.put("scenic_name", scenic_name);
                scenicInfo.put("type_id", scenicTypeId);
                scenicInfo.put("scenic_content", scenic_content);
                scenicInfo.put("scenic_image", image_show);
                scenicInfo.put("city_name", city_name);
                adminManager.addScenicInfo(scenicInfo);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isCheck = true;
        String s = "";
        switch (requestCode) {
            case ProjectProperties.FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    s = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                } else {
                    isCheck = false;
                }
                break;
            case ProjectProperties.FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    s = getFilesDir() + File.separator + "images" + File.separator + phone_number + timeStamp + ".jpg";
                } else {
                    isCheck = false;
                }
                break;
            default:
                break;
        }
        if (isCheck) {
            Glide.with(this).load(s).into(iv_scenic_image_show);
            image_show = s;
            iv_delete_scenic_image_show.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

