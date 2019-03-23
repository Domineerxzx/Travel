package com.changxinyue.graduationproject.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.fragments.BottomFragment;
import com.changxinyue.graduationproject.fragments.FirstPageFragment;

public class MainActivity extends Activity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, new FirstPageFragment());
        transaction.replace(R.id.fl_bottom, new BottomFragment());
        transaction.commit();
    }
}
