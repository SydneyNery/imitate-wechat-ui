package com.virtual.plan.tomatoplan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.virtual.plan.tomatoplan.R;
import com.virtual.plan.tomatoplan.activity.CloudContentActivity;
import com.virtual.plan.tomatoplan.ui.ImageViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    private CloudContentActivity mCloudContentActivity;
    private View mHomeLayout;

    private List<View> views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mHomeLayout = inflater.inflate(R.layout.home_fragment, container, false);
        mCloudContentActivity = (CloudContentActivity) getActivity();
        mFragmentManager = getActivity().getSupportFragmentManager();
        initViewPager();
        return mHomeLayout;
    }

    private void initViewPager() {
        views = new ArrayList<>();

        ImageView imageView = new ImageView(mCloudContentActivity);
        imageView.setImageResource(R.drawable.amsterdam);
        views.add(imageView);
        imageView = new ImageView(mCloudContentActivity);
        imageView.setImageResource(R.drawable.aerial_view_of_glaciers);
        views.add(imageView);
        imageView = new ImageView(mCloudContentActivity);
        imageView.setImageResource(R.drawable.butterfly);
        views.add(imageView);
        imageView = new ImageView(mCloudContentActivity);
        imageView.setImageResource(R.drawable.colors_of_dawn_in_venice);
        views.add(imageView);
        imageView = new ImageView(mCloudContentActivity);
        imageView.setImageResource(R.drawable.little_one_and_the_fish);
        views.add(imageView);

        ImageViewPager pager = (ImageViewPager) mHomeLayout.findViewById(R.id.image_view_pager);
        pager.setViewPagerViews(views);
    }
}
