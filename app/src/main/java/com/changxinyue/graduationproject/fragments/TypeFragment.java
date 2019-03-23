package com.changxinyue.graduationproject.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.activities.ScenicActivity;
import com.changxinyue.graduationproject.adapters.TypeContentAdapter;
import com.changxinyue.graduationproject.adapters.TypeNameAdapter;
import com.changxinyue.graduationproject.beans.ScenicInfo;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.TypeManager;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends Fragment implements AdapterView.OnItemClickListener, OnItemClickListener {

    private View fragment_type;
    private ListView lv_type_name;
    private RecyclerView rv_type_content;
    private TypeManager typeManager;
    private List<ScenicTypeInfo> scenicTypeInfoList;
    private List<ScenicInfo> scenicInfoList;
    private TypeNameAdapter typeNameAdapter;
    private TypeContentAdapter typeContentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_type = inflater.inflate(R.layout.fragment_type, null);
        initView();
        initData();
        return fragment_type;
    }

    private void initData() {
        typeManager = new TypeManager(getActivity());
        scenicTypeInfoList = typeManager.getScenicTypeInfoList();
        typeNameAdapter = new TypeNameAdapter(getActivity(), scenicTypeInfoList);
        lv_type_name.setAdapter(typeNameAdapter);
        if (scenicTypeInfoList.size() > 0) {
            scenicInfoList = typeManager.getScenicInfoList(scenicTypeInfoList.get(0).getType_id());
        }else{
            scenicInfoList = new ArrayList<>();
        }
        typeContentAdapter = new TypeContentAdapter(getActivity(), scenicInfoList);
        rv_type_content.setAdapter(typeContentAdapter);
        typeContentAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        lv_type_name = fragment_type.findViewById(R.id.lv_type_name);
        lv_type_name.setOnItemClickListener(this);
        rv_type_content = fragment_type.findViewById(R.id.rv_type_content);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_type_content.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        scenicInfoList = typeManager.getScenicInfoList(scenicTypeInfoList.get(position).getType_id());
        typeContentAdapter = new TypeContentAdapter(getActivity(), scenicInfoList);
        rv_type_content.setAdapter(typeContentAdapter);
        typeContentAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ScenicActivity.class);
        intent.putExtra("ScenicInfo", scenicInfoList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
