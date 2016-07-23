package com.virtual.plan.tomatoplan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtual.plan.tomatoplan.R;


public class DestinationFragment extends BaseFragment {

    private View mDestinationLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDestinationLayout = inflater.inflate(R.layout.destination_fragment, container, false);
        mFragmentManager = getActivity().getSupportFragmentManager();
        return mDestinationLayout;
    }
}
