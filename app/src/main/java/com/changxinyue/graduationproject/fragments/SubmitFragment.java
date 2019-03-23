package com.changxinyue.graduationproject.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.PhotoWallAdapter;
import com.changxinyue.graduationproject.adapters.SubmitAdapter;
import com.changxinyue.graduationproject.beans.SubmitImageInfo;
import com.changxinyue.graduationproject.beans.SubmitInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.SubmitManager;
import com.changxinyue.graduationproject.properties.ProjectProperties;
import com.changxinyue.graduationproject.utils.dialogUtils.ChooseUserHeadDialogUtil;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class SubmitFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    private View fragment_submit;
    private RecyclerView rv_submit;
    private Button bt_submit;
    private EditText et_submit_content;
    private String phone_number;
    private long timeStamp;
    private SubmitAdapter submitAdapter;
    private SubmitManager submitManager;
    private SharedPreferences userInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_submit = inflater.inflate(R.layout.fragment_submit, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_submit;
    }

    private void initData() {
        submitAdapter = new SubmitAdapter(getActivity(), new ArrayList<String>());
        rv_submit.setAdapter(submitAdapter);
        submitAdapter.setOnItemClickListener(this);
        submitManager = new SubmitManager(getActivity());
    }

    private void setOnClickListener() {
        bt_submit.setOnClickListener(this);
    }

    private void initView() {
        rv_submit = (RecyclerView) fragment_submit.findViewById(R.id.rv_submit);
        rv_submit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        bt_submit = (Button) fragment_submit.findViewById(R.id.bt_submit);
        et_submit_content = (EditText) fragment_submit.findViewById(R.id.et_submit_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (phone_number == null || phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，不能发布信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setPhone_number(phone_number);
                submitInfo.setNickname(userInfo.getString("nickname", ""));
                submitInfo.setUser_head(userInfo.getString("userHead", ""));
                submitInfo.setSubmit_content(et_submit_content.getText().toString().trim());
                long submit_id = submitManager.UploadSubmitInfo(submitInfo);
                List<String> data = submitAdapter.getData();
                if (data.size() > 0 && data.size() < 9) {
                    data.remove(data.size() - 1);
                }
                for (String s : data) {
                    SubmitImageInfo submitImageInfo = new SubmitImageInfo();
                    submitImageInfo.setSubmit_id(submit_id);
                    submitImageInfo.setSubmit_image(s);
                    submitManager.UploadSubmitImageInfo(submitImageInfo);
                }
                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                et_submit_content.setText("");
                submitAdapter.setData(new ArrayList<String>());
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        userInfo = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        timeStamp = System.currentTimeMillis();
        ChooseUserHeadDialogUtil.showSelectSubmitDialog(getActivity(), this, phone_number, timeStamp);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isCheck = true;
        String s = "";
        switch (requestCode) {
            case ProjectProperties.FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    s = data.getData().toString();
                } else {
                    isCheck = false;
                }
                break;
            case ProjectProperties.FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    s = getActivity().getFilesDir() + File.separator + "images" + File.separator + phone_number + timeStamp + ".jpg";
                } else {
                    isCheck = false;
                }
                break;
            default:
                break;
        }
        if (isCheck) {
            List<String> strings = submitAdapter.getData();
            if (strings.size() == 0) {
                strings.add(s);
            } else {
                strings.remove(strings.size() - 1);
                strings.add(s);
            }
            if (strings.size() != 9) {
                strings.add("");
            }
            submitAdapter.setData(strings);
        } else {
            Toast.makeText(getActivity(), "取消选择", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
