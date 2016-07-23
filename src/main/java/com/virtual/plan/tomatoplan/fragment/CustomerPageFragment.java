package com.virtual.plan.tomatoplan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtual.plan.tomatoplan.R;

public class CustomerPageFragment extends BaseFragment {

    private View mAccountLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mAccountLayout = inflater.inflate(R.layout.account_fragment, container, false);
        mFragmentManager = getActivity().getSupportFragmentManager();
        return mAccountLayout;
    }
}
