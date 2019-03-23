package com.changxinyue.graduationproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.activities.CitiesActivity;
import com.changxinyue.graduationproject.activities.SearchActivity;
import com.changxinyue.graduationproject.activities.TravelNoteActivity;
import com.changxinyue.graduationproject.adapters.RecommendCitiesAdapter;
import com.changxinyue.graduationproject.adapters.RecommendTravelNotesAdapter;
import com.changxinyue.graduationproject.beans.CityInfo;
import com.changxinyue.graduationproject.beans.TravelNoteInfo;
import com.changxinyue.graduationproject.interfaces.OnItemClickListener;
import com.changxinyue.graduationproject.managers.FirstPageManager;
import com.changxinyue.graduationproject.utils.dialogUtils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends Fragment implements View.OnClickListener,OnItemClickListener {

    private View fragment_first_page;
    private Banner bn_banner;
    private RecyclerView rv_recommend_travel_notesnd;
    private List<String> images;
    private RecyclerView rv_recommend_cities;
    private RelativeLayout rl_search;
    private TextView tv_search;
    private ImageView iv_search;
    private FirstPageManager firstPageManager;
    private List<CityInfo> cityRecommendInfoList;
    private RecommendCitiesAdapter recommendCitiesAdapter;
    private List<String> bannerImageList;
    private List<TravelNoteInfo> travelNoteInfoList;
    private RecommendTravelNotesAdapter travelNotesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_first_page = inflater.inflate(R.layout.fragment_first_page, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_first_page;
    }

    private void setOnClickListener() {
        rl_search.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
    }

    private void initData() {
        firstPageManager = new FirstPageManager(getActivity());
        bannerImageList = firstPageManager.getBannerImageList();
        bn_banner.setImages(bannerImageList);
        bn_banner.setImageLoader(new GlideImageLoader());
        bn_banner.isAutoPlay(true);
        bn_banner.setDelayTime(5000);
        bn_banner.setIndicatorGravity(BannerConfig.CENTER);bn_banner.start();
        cityRecommendInfoList = firstPageManager.getCityRecommendInfoList();
        recommendCitiesAdapter = new RecommendCitiesAdapter(getActivity(), cityRecommendInfoList);
        rv_recommend_cities.setAdapter(recommendCitiesAdapter);
        recommendCitiesAdapter.setOnItemClickListener(this);
        travelNoteInfoList = firstPageManager.getTravelNoteInfoList();
        travelNotesAdapter = new RecommendTravelNotesAdapter(getActivity(),travelNoteInfoList);
        travelNotesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TravelNoteActivity.class);
                intent.putExtra("TravelNoteInfo",travelNoteInfoList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        rv_recommend_travel_notesnd.setAdapter(travelNotesAdapter);
    }

    private void initView() {
        rl_search = fragment_first_page.findViewById(R.id.rl_search);
        rl_search.bringToFront();
        tv_search = fragment_first_page.findViewById(R.id.tv_search);
        iv_search = fragment_first_page.findViewById(R.id.iv_search);
        bn_banner = fragment_first_page.findViewById(R.id.bn_banner);
        rv_recommend_travel_notesnd = fragment_first_page.findViewById(R.id.rv_recommend_travel_notes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend_travel_notesnd.setLayoutManager(gridLayoutManager);
        rv_recommend_cities = fragment_first_page.findViewById(R.id.rv_recommend_cities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_recommend_cities.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
            case R.id.rl_search:
            case R.id.iv_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), CitiesActivity.class);
        intent.putExtra("CityInfo",cityRecommendInfoList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
