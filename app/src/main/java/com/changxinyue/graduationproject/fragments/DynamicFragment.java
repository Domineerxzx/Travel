package com.changxinyue.graduationproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.adapters.DynamicAdapter;
import com.changxinyue.graduationproject.beans.SubmitInfo;
import com.changxinyue.graduationproject.managers.DynamicManger;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DynamicFragment extends Fragment {

    private View fragment_dynamic;
    private ListView lv_dynamic;
    private DynamicManger dynamicManger;
    private List<SubmitInfo> submitInfoList;
    private TextView tv_tip;
    private SharedPreferences userInfo;
    private String phone_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_dynamic = inflater.inflate(R.layout.fragment_dynamic, null);
        initView();
        initData();
        return fragment_dynamic;
    }

    private void initData() {
        userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        if(phone_number.length() != 11){
            lv_dynamic.setVisibility(View.GONE);
            tv_tip.setVisibility(View.VISIBLE);
        }else{
            lv_dynamic.setVisibility(View.VISIBLE);
            tv_tip.setVisibility(View.GONE);
            dynamicManger = new DynamicManger(getActivity());
            submitInfoList = dynamicManger.getSubmitInfoList();
            lv_dynamic.setAdapter(new DynamicAdapter(getActivity(),submitInfoList));
        }
    }

    private void initView() {
        lv_dynamic = (ListView) fragment_dynamic.findViewById(R.id.lv_dynamic);
        tv_tip = (TextView)fragment_dynamic.findViewById(R.id.tv_tip);
    }
}
