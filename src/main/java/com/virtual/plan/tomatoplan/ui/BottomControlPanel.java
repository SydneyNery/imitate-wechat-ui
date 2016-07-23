package com.virtual.plan.tomatoplan.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.virtual.plan.tomatoplan.R;
import com.virtual.plan.tomatoplan.constant.Constant;
import com.virtual.plan.tomatoplan.constant.Constant.BottomState;

import java.util.ArrayList;
import java.util.List;

public class BottomControlPanel extends RelativeLayout implements OnTouchListener {

    private static final String TAG = "BottomControlPanel";
    private ImageText mHomeBtn = null;
    private ImageText mDestination = null;
    private ImageText mCustomerBtn = null;
    private int DEFAULT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //Color.rgb(192, 192, 192)
    private BottomPanelCallback mBottomCallback = null;
    private List<ImageText> viewList = new ArrayList<>();

    public interface BottomPanelCallback {
        void onBottomPanelClick(int itemId);
    }

    public BottomControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHomeBtn = (ImageText)findViewById(R.id.btn_home);
        mDestination = (ImageText)findViewById(R.id.btn_destination);
        mCustomerBtn = (ImageText)findViewById(R.id.btn_customer_setting);
        setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        viewList.add(mHomeBtn);
        viewList.add(mDestination);
        viewList.add(mCustomerBtn);

    }

    public void initBottomPanel() {
        if(mHomeBtn != null){
            mHomeBtn.setImage(R.drawable.img_discover_nor);
            mHomeBtn.setText(R.string.bottom_btn_search);
            mHomeBtn.setAlpha(1);
        }
        if(mDestination != null){
            mDestination.setImage(R.drawable.img_dest_nor);
            mDestination.setText(R.string.bottom_btn_destination);
            mDestination.setAlpha(1);
        }
        if(mCustomerBtn != null){
            mCustomerBtn.setImage(R.drawable.img_mine_nor);
            mCustomerBtn.setText(R.string.bottom_btn_account);
            mCustomerBtn.setAlpha(1);
        }
        setBtnListener();
    }

    private void setBtnListener() {
        int num = this.getChildCount();
        for(int i = 0; i < num; i++){
            View v = getChildAt(i);
            if(v != null){
                v.setOnTouchListener(this);
            }
        }
    }

    public void setBottomCallback(BottomPanelCallback bottomCallback) {
        mBottomCallback = bottomCallback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        initBottomPanel();
        int index = -1;
        switch (v.getId()) {
        case R.id.btn_home:
            index = BottomState.HOME;
            mHomeBtn.setChecked(R.drawable.img_discover_press);
            break;
        case R.id.btn_destination:
            index = BottomState.DESTINATION;
            mDestination.setChecked(R.drawable.img_dest_press);
            break;
        case R.id.btn_customer_setting:
            index = BottomState.CUSTOMER;
            mCustomerBtn.setChecked(R.drawable.img_mine_press);
            break;
        default:
            break;
        }

        if (mBottomCallback != null) {
            mBottomCallback.onBottomPanelClick(index);
        }
        return true;
    }

    public void defaultBtnChecked() {
        if(mHomeBtn != null){
            mHomeBtn.setChecked(R.drawable.img_discover_press);
        }
    }

    public void currentBtnChecked(int index) {
        initBottomPanel();
        switch(index){
        case BottomState.HOME:
            mHomeBtn.setChecked(R.drawable.img_discover_press);
            break;
        case BottomState.DESTINATION:
            mDestination.setChecked(R.drawable.img_dest_press);
            break;
        case BottomState.CUSTOMER:
            mCustomerBtn.setChecked(R.drawable.img_mine_press);
            break;
        default:break;
        }
    }

    public void setBtnAlpha(float currentAlpha, float nextAlpha,
                            int position, int nextPosition) {
        switch(position) {
            case BottomState.HOME:
                if (currentAlpha < 0.5f) {
                    mHomeBtn.setUnChecked(R.drawable.img_discover_nor);
                    mHomeBtn.setAlpha(1 - currentAlpha);
                } else {
                    mHomeBtn.setChecked(R.drawable.img_discover_press);
                    mHomeBtn.setAlpha(currentAlpha);
                }
                break;
            case BottomState.DESTINATION:
                if (currentAlpha < 0.5f) {
                    mDestination.setUnChecked(R.drawable.img_dest_nor);
                    mDestination.setAlpha(1 - currentAlpha);
                } else {
                    mDestination.setChecked(R.drawable.img_dest_press);
                    mDestination.setAlpha(currentAlpha);
                }
                break;
            case BottomState.CUSTOMER:
                if (currentAlpha < 0.5f) {
                    mCustomerBtn.setUnChecked(R.drawable.img_mine_nor);
                    mCustomerBtn.setAlpha(1 - currentAlpha);
                } else {
                    mCustomerBtn.setChecked(R.drawable.img_mine_press);
                    mCustomerBtn.setAlpha(currentAlpha);
                }
                break;
            default:break;
        }
        switch(nextPosition) {
            case BottomState.HOME:
                if (nextAlpha < 0.5f) {
                    mHomeBtn.setUnChecked(R.drawable.img_discover_nor);
                    mHomeBtn.setAlpha(1 - nextAlpha);
                } else {
                    mHomeBtn.setAlpha(nextAlpha);
                    mHomeBtn.setChecked(R.drawable.img_discover_press);
                }
                break;
            case BottomState.DESTINATION:
                if (nextAlpha < 0.5f) {
                    mDestination.setUnChecked(R.drawable.img_dest_nor);
                    mDestination.setAlpha(1 - nextAlpha);
                } else {
                    mDestination.setChecked(R.drawable.img_dest_press);
                    mDestination.setAlpha(nextAlpha);
                }
                break;
            case BottomState.CUSTOMER:
                if (nextAlpha < 0.5f) {
                    mCustomerBtn.setUnChecked(R.drawable.img_mine_nor);
                    mCustomerBtn.setAlpha(1 - nextAlpha);
                } else {
                    mCustomerBtn.setChecked(R.drawable.img_mine_press);
                    mCustomerBtn.setAlpha(nextAlpha);
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutItems(left, top, right, bottom);
    }

    /**
     * 子控件在父布局中左右的间距一致
     */
    private void layoutItems(int left, int top, int right, int bottom) {
        final int count = getChildCount();
        if (count == 0) {
            return;
        }
        int width = right - left;
        int height = bottom - top;
        int totalWidth = 0;
        int margin = (int) getResources().getDimension(R.dimen.image_text_middle_margin);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            childHeight = childHeight - margin;
            int space = (width - childWidth * count) / (2 * count);
            child.layout(space + totalWidth, height / 2 - childHeight / 2,
                    childWidth + space + totalWidth, childHeight + (height / 2 - childHeight / 2));
            if (Constant.LOG_GLOBAL) {
                Log.d(TAG, "Panel Width : " + width +
                            "Child width : " + childWidth +
                            "Space : " + space + 
                            "totalWidth :" + totalWidth);
            }
            totalWidth += 2 * space + childWidth; 
        }
    }

}
