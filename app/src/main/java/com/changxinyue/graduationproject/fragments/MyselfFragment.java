package com.changxinyue.graduationproject.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.activities.AboutUsActivity;
import com.changxinyue.graduationproject.activities.ChooseIdentityActivity;
import com.changxinyue.graduationproject.activities.CollectionActivity;
import com.changxinyue.graduationproject.activities.FeedbackActivity;
import com.changxinyue.graduationproject.activities.SettingActivity;
import com.changxinyue.graduationproject.activities.UserInfoActivity;
import com.changxinyue.graduationproject.adapters.MyselfAdapter;
import com.changxinyue.graduationproject.utils.PermissionUtil;
import com.changxinyue.graduationproject.utils.dialogUtils.TwoButtonDialog;
import com.changxinyue.graduationproject.utils.intentUtils.PermissionController;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;

public class MyselfFragment extends Fragment implements View.OnClickListener {

    private View fragment_myself;
    private RecyclerView rv_myself;
    private TextView tv_nickname;
    private ImageView iv_user_head;
    private TextView tv_username;
    private ImageView iv_setting;
    private TextView tv_setting;
    private RelativeLayout rl_setting;
    private RelativeLayout rl_contact_us;
    private ImageView iv_contact_us;
    private TextView tv_contact_us;
    private RelativeLayout rl_about_us;
    private ImageView iv_about_us;
    private TextView tv_about_us;
    private RelativeLayout rl_feedback;
    private ImageView iv_feedback;
    private TextView tv_feedback;
    private RelativeLayout rl_collection;
    private ImageView iv_collection;
    private TextView tv_collection;
    private String phone_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_myself = inflater.inflate(R.layout.fragment_myself, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_myself;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "暂无登录信息");
        String nickname = userInfo.getString("nickname", "点击  登录/注册");
        String userHead = userInfo.getString("userHead", "");
        if (userHead.length() != 0) {
            Glide.with(getActivity()).load(Uri.parse(userHead)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        } else {
            Glide.with(getActivity()).load(R.drawable.user_head_default).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        }
        if (phone_number.equals("暂无登录信息")) {
            tv_username.setText(phone_number);
        } else {
            tv_username.setText("ID:" + phone_number);
        }
        tv_nickname.setText(nickname);
    }

    private void setOnClickListener() {
        tv_nickname.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_contact_us.setOnClickListener(this);
        iv_contact_us.setOnClickListener(this);
        tv_contact_us.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        iv_about_us.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        iv_feedback.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        iv_collection.setOnClickListener(this);
        tv_collection.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        tv_nickname = fragment_myself.findViewById(R.id.tv_nickname);
        iv_user_head = fragment_myself.findViewById(R.id.iv_user_head);
        tv_username = fragment_myself.findViewById(R.id.tv_username);
        iv_setting = fragment_myself.findViewById(R.id.iv_setting);
        tv_setting = fragment_myself.findViewById(R.id.tv_setting);
        rl_setting = fragment_myself.findViewById(R.id.rl_setting);
        rl_contact_us = fragment_myself.findViewById(R.id.rl_contact_us);
        iv_contact_us = fragment_myself.findViewById(R.id.iv_contact_us);
        tv_contact_us = fragment_myself.findViewById(R.id.tv_contact_us);
        rl_about_us = fragment_myself.findViewById(R.id.rl_about_us);
        iv_about_us = fragment_myself.findViewById(R.id.iv_about_us);
        tv_about_us = fragment_myself.findViewById(R.id.tv_about_us);
        rl_feedback = fragment_myself.findViewById(R.id.rl_feedback);
        iv_feedback = fragment_myself.findViewById(R.id.iv_feedback);
        tv_feedback = fragment_myself.findViewById(R.id.tv_feedback);
        rl_collection = fragment_myself.findViewById(R.id.rl_collection);
        iv_collection = fragment_myself.findViewById(R.id.iv_collection);
        tv_collection = fragment_myself.findViewById(R.id.tv_collection);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_nickname:
            case R.id.iv_user_head:
            case R.id.tv_username:
                SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String phone_number = userInfo.getString("phone_number", "暂无登录信息");
                String nickname = userInfo.getString("nickname", "点击  登录/注册");
                if (phone_number.equals("暂无登录信息") && nickname.equals("点击  登录/注册")) {
                    Intent login = new Intent(getActivity(), ChooseIdentityActivity.class);
                    startActivity(login);
                    getActivity().finish();
                    break;
                } else {
                    Intent user_info = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(user_info);
                    break;
                }
            case R.id.rl_setting:
            case R.id.iv_setting:
            case R.id.tv_setting:
                Intent setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(setting);
                break;
            case R.id.rl_collection:
            case R.id.iv_collection:
            case R.id.tv_collection:
                if(this.phone_number.length() != 11){
                    Toast.makeText(getActivity(), "还没登录呢，不能查看收藏", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent collection = new Intent(getActivity(), CollectionActivity.class);
                startActivity(collection);
                break;
            case R.id.rl_contact_us:
            case R.id.iv_contact_us:
            case R.id.tv_contact_us:
                PermissionUtil.requestPower(getActivity(), getActivity(), Manifest.permission.CALL_PHONE);
                TwoButtonDialog contact_us = new TwoButtonDialog();
                String title = "联系我们";
                String message = "拨打电话：15710573129";
                final String telephone = "15710573129";
                contact_us.show(title, message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "未授权拨打电话,请设置开启权限", Toast.LENGTH_SHORT).show();
                            PermissionController.gotoMeizuPermission(getActivity());
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + telephone));
                            getActivity().startActivity(intent);
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "取消呼叫", Toast.LENGTH_SHORT).show();
                    }
                }, getActivity().getFragmentManager());
                break;
            case R.id.rl_about_us:
            case R.id.iv_about_us:
            case R.id.tv_about_us:
                Intent about_us = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(about_us);
                break;
            case R.id.rl_feedback:
            case R.id.iv_feedback:
            case R.id.tv_feedback:
                if (this.phone_number.length() != 11) {
                    Toast.makeText(getActivity(), "还没登录呢，快去登录吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent feedback = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(feedback);
                break;
        }
    }
}
