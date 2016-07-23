package com.virtual.plan.tomatoplan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Window;

import com.virtual.plan.tomatoplan.R;
import com.virtual.plan.tomatoplan.constant.Constant;
import com.virtual.plan.tomatoplan.constant.Constant.BottomState;
import com.virtual.plan.tomatoplan.fragment.BaseFragment;
import com.virtual.plan.tomatoplan.fragment.CustomerPageFragment;
import com.virtual.plan.tomatoplan.fragment.DestinationFragment;
import com.virtual.plan.tomatoplan.fragment.HomeFragment;
import com.virtual.plan.tomatoplan.ui.BottomControlPanel;

public class CloudContentActivity extends FragmentActivity
        implements BottomControlPanel.BottomPanelCallback {

    /**
     * 承载Fragment的ViewPager
     */
    private ViewPager mViewPager;
    private ContentPageAdapter mAdapter;

    private FragmentManager mFragmentManager;
    private FragmentPageChangeListener mPageChangeListener;
    private HomeFragment mHomeFragment;
    private DestinationFragment mDestinationFragment;
    private CustomerPageFragment mCustomerPageFragment;
    private BottomControlPanel mBottomPanel = null;
    private static String currFragTag = "";
    private int mLastIndex = 0;
    public int currentPosition = 0;
    private boolean isClick = false;


    private float beginMoveOffsetPixels = 0f;
    private float lastMoveOffsetPixels = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cloud_content_activity);

        mFragmentManager = getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.fragment_pager);
        mAdapter = new ContentPageAdapter(mFragmentManager);
        mViewPager.setAdapter(mAdapter);
        mPageChangeListener = new FragmentPageChangeListener();
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        createFragmentAndView();
    }

    private void createFragmentAndView() {
        mBottomPanel = (BottomControlPanel)findViewById(R.id.bottom_panel);
        if (mBottomPanel != null) {
            mBottomPanel.initBottomPanel();
            mBottomPanel.setBottomCallback(this);
        }
        setDefaultFirstFragment(Constant.FRAGMENT_FLAG_HOME);
    }

    private String getTagByIndex(int index) {
        String tag;
        switch (index) {
        case BottomState.HOME:
            tag = Constant.FRAGMENT_FLAG_HOME;
            break;
        case BottomState.DESTINATION:
            tag = Constant.FRAGMENT_FLAG_DESTINATION;
            break;
        case BottomState.CUSTOMER:
            tag = Constant.FRAGMENT_FLAG_CUSTOMER;
            break;
        default:
            tag = Constant.FRAGMENT_FLAG_HOME;
            break;
        }
        return tag;
    }

    private Fragment getFragment(String tag) {
        Fragment f = mFragmentManager.findFragmentByTag(tag);

        if(f == null){
            f = BaseFragment.newInstance(tag);
        }
        if (f instanceof HomeFragment) {
            if (mHomeFragment == null) {
                mHomeFragment = (HomeFragment) f;
            }
            return mHomeFragment;
        } else if ( f instanceof DestinationFragment) {
            if (mDestinationFragment == null) {
                mDestinationFragment = (DestinationFragment) f;
            }
            return mDestinationFragment;
        } else if (f instanceof CustomerPageFragment) {
            if (mCustomerPageFragment == null) {
                mCustomerPageFragment = (CustomerPageFragment) f;
            }
            return mCustomerPageFragment;
        }

        return f;
    }

    private Fragment getFragment(int index) {
        String tag = getTagByIndex(index);
        return getFragment(tag);
    }

    private void setDefaultFirstFragment(String tag) {
        mBottomPanel.defaultBtnChecked();
        if (!TextUtils.equals(tag, currFragTag)) {
            currFragTag = tag;
        }
    }

    @Override
    public void onBottomPanelClick(int itemId) {
        isClick = true;
        mViewPager.setCurrentItem(itemId);
        currFragTag = getTagByIndex(itemId);
    }


    private void updatePosition(int position) {
        if (currentPosition != position) {
            currentPosition = position;
        }
    }

    /**
     *
     * @param position 当viewpager右滑时position为当前的pager,
     *                 左滑时currentPosition为当前的pager
     * @param positionOffset 偏移量用来表示透明度.
     * 该方法用来改变底部标示物的透明度,根据用户滑动ViewPager的距离来计算透明度.
     */
    private void changeBottomPanelColor(int position, float positionOffset) {
        if (positionOffset == 0f || positionOffset < 0.001
                //viewPager从左到右滑过中间的一个,要把其实offset置为0
                || Math.abs(positionOffset - lastMoveOffsetPixels) > 0.9f) {
            beginMoveOffsetPixels = 0f;
            if (Math.abs(positionOffset - lastMoveOffsetPixels) > 0.9f) {
                lastMoveOffsetPixels = positionOffset;
            } else {
                lastMoveOffsetPixels = 0f;
            }
            return;
        }
        if (beginMoveOffsetPixels == 0f) {
            beginMoveOffsetPixels = positionOffset;
            if (beginMoveOffsetPixels > 0.9f) {
                beginMoveOffsetPixels = 1f;
            } else if (beginMoveOffsetPixels < 0.1f) {
                beginMoveOffsetPixels = 0.00001f;
            }
        }

        float btnAlpha;
        int nextPosition;
        if (position == 0) {
            nextPosition = position + 1;
            btnAlpha = 1 - positionOffset;
        } else if (position == BottomState.TOTAL_COUNT - 1) {
            nextPosition = position - 1;
            btnAlpha = positionOffset;
        } else {
            if (positionOffset < beginMoveOffsetPixels) {
                //左滑
                nextPosition = position - 1;
                btnAlpha = positionOffset;
            } else if (positionOffset > beginMoveOffsetPixels) {
                //右滑
                nextPosition = position + 1;
                btnAlpha = 1 - positionOffset;
            } else {
                if (positionOffset < lastMoveOffsetPixels) {
                    //左滑
                    nextPosition = position - 1;
                    btnAlpha = positionOffset;
                } else if (positionOffset > lastMoveOffsetPixels && lastMoveOffsetPixels != 0f) {
                    //右滑
                    nextPosition = position + 1;
                    btnAlpha = 1 - positionOffset;
                } else {
                    return;
                }
            }

        }
        lastMoveOffsetPixels = positionOffset;
        mBottomPanel.setBtnAlpha(btnAlpha, 1 - btnAlpha, position, nextPosition);
    }

    private final class FragmentPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    updatePosition(mLastIndex);
                    isClick = false;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position < currentPosition) {
                position = currentPosition;
            }
            if (!isClick) {
                changeBottomPanelColor(position, positionOffset);
            }
        }

        @Override
        public void onPageSelected(int index) {
            currFragTag = getTagByIndex(index);
            //mBottomPanel.currentBtnChecked(index);
            mLastIndex = index;
        }

    }

    private class ContentPageAdapter extends FragmentPagerAdapter {

        public ContentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return getFragment(arg0);
        }

        @Override
        public int getCount() {
            return BottomState.TOTAL_COUNT;
        }

    }

}
