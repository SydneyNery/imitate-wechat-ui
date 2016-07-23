package com.virtual.plan.tomatoplan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtual.plan.tomatoplan.constant.Constant;


public class BaseFragment extends Fragment {
    protected FragmentManager mFragmentManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return  super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static BaseFragment newInstance(String tag){
        BaseFragment baseFragment =  null;
        if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_HOME)) {
            baseFragment = new HomeFragment();
        } else if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_DESTINATION)) {
            baseFragment = new DestinationFragment();
        } else if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_CUSTOMER)) {
            baseFragment = new CustomerPageFragment();
        }
        return baseFragment;
    }

}

