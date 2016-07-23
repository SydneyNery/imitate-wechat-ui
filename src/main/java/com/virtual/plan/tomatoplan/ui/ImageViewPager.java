package com.virtual.plan.tomatoplan.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.virtual.plan.tomatoplan.R;

import java.util.List;

public class ImageViewPager extends RelativeLayout implements Runnable {

    public static final String TAG = "ImageViewPager";

    private static final int ID_VIEWPAGER = 1;
    private static final int ID_DOTS = 2;
    private static final int NONE_CHANGE_INTERVAL = -1;
    private static final int DEFAULT_CHANGE_INTERVAL = 3000;
    private static final int MAX_CHANGE_INTERVAL = 5000;
    private static final int MIN_CHANGE_INTERVAL = 1000;

    private ViewPager viewPager;
    private LinearLayout viewDots;// points parents layout
    private ImageView[] dots;// points stuff
    private List<View> views;

    private int position = 0;//current position
    private boolean isContinue = true;//is auto playing

    private float dotsViewHeight;
    private float dotsSpacing;
    private Drawable dotsFocusImage;
    private Drawable dotsBlurImage;
    private int scaleTypeIndex;
    private int dotsBackgroundColor;
    private float dotsBgAlpha;
    private int changeInterval;

    private ScaleType mScaleType;

    private static final ScaleType[] sScaleTypeArray = { ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START,
        ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE };

    Handler pageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
    };

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.imageViewPager, 0, 0);

        try {
            dotsViewHeight = a.getDimension(
                        R.styleable.imageViewPager_dotsViewHeight, 40);
            dotsSpacing = a.getDimension(
                    R.styleable.imageViewPager_dotsSpacing, 40);
            dotsBackgroundColor = a.getColor(R.styleable.imageViewPager_dotsBackgroundColor, Color.BLACK);
            dotsBgAlpha = a.getFloat(R.styleable.imageViewPager_dotsBgAlpha, 1f);//默认不透明
            changeInterval = a.getInt(R.styleable.imageViewPager_changeInterval, NONE_CHANGE_INTERVAL);
            dotsFocusImage = a.getDrawable(R.styleable.imageViewPager_dotsFocusImage);
            dotsBlurImage = a.getDrawable(R.styleable.imageViewPager_dotsBlurImage);
            scaleTypeIndex = a.getInt(R.styleable.imageViewPager_android_scaleType, -1);
            if (scaleTypeIndex >= 0) {
                setScaleType(sScaleTypeArray[scaleTypeIndex]);
            }
        } finally {
            a.recycle();
        }

        initView();
    }


    @SuppressLint("NewApi")
    private void initView() {
        viewPager = new ViewPager(getContext());
        viewDots = new LinearLayout(getContext());

        viewPager.setId(ID_VIEWPAGER);
        viewDots.setId(ID_DOTS);
        viewDots.setAlpha(dotsBgAlpha);
        viewDots.setBackgroundColor(dotsBackgroundColor);
        viewDots.setMinimumHeight((int) dotsViewHeight);
        viewDots.setOrientation(LinearLayout.HORIZONTAL);
        viewDots.setGravity(Gravity.CENTER);

        LayoutParams lp1 = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(viewPager, lp1);

        LayoutParams lp2 = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp2.addRule(RelativeLayout.ABOVE, ID_VIEWPAGER);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(viewDots, lp2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = this.getChildAt(0);
        child.layout(0, 0, getWidth(), getHeight());

        if (changed) {
            child = this.getChildAt(1);
            child.measure(r - l, (int) dotsViewHeight);
            child.layout(0, getHeight() - (int) dotsViewHeight, getWidth(),
                    getHeight());
        }
    }

    /**
     * Controls how the image should be resized or moved to match the size of this ImageView.
     * 
     * @param scaleType
     *            The desired scaling mode.
     * 
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    public void setScaleType(final ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
            case MATRIX:
                mScaleType = ScaleType.MATRIX;
            case FIT_XY:
                mScaleType = ScaleType.FIT_XY;
            case FIT_START:
                mScaleType = ScaleType.FIT_START;
            case FIT_CENTER:
                mScaleType = ScaleType.FIT_CENTER;
            case FIT_END:
                mScaleType = ScaleType.FIT_END;
            case CENTER:
                mScaleType = ScaleType.CENTER;
            case CENTER_CROP:
                mScaleType = ScaleType.CENTER_CROP;
                break;
            case CENTER_INSIDE:
                mScaleType = ScaleType.CENTER_INSIDE;
                break;
            }
        }
    }

    /**
     * Return the current scale type in use by inner ImageViews.
     * 
     * @see android.widget.ImageView.ScaleType
     * 
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    public ScaleType getScaleType() {
        return mScaleType;
    }

    public void setViewPagerViews(List<View> views) {
        this.views = views;
        addDots(views.size());

        viewPager.setAdapter(new ImageViewPagerAdapter(views, mScaleType));

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                position = index;
                switchToDot(index);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
        });
        
        viewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                switch (motionevent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                case MotionEvent.ACTION_UP:
                    isContinue = true;
                    break;
                default:
                    isContinue = true;
                    break;
                }
                return false;
            }
        });
        new Thread(this).start();
    }

    /**
     * Add the bottom image view
     * @param size
     */
    private void addDots(int size) {
        dots = new ImageView[size];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                25, 25);

        for (int i = 0; i < size; i++) {
            dots[i] = new ImageView(getContext());
            if (i != size - 1) {
                layoutParams.setMargins((int) dotsSpacing, 0, 0, 0);
            }
            dots[i].setLayoutParams(layoutParams);
            if (i == 0) {
                dots[i].setImageDrawable(dotsFocusImage);
            } else {
                dots[i].setImageDrawable(dotsBlurImage);
            }
            dots[i].setAlpha(1f);
            viewDots.addView(dots[i]);
        }
        requestLayout();
    }

    /**
     * Change the dot to current page
     * @param index current page
     */
    private void switchToDot(int index) {
        if (index < 0 || index > views.size() - 1) {
            return;
        }
        dots[index].setImageDrawable(dotsFocusImage);
        for (int i = 0; i < dots.length; i++) {
            if (index != i) {
                dots[i].setImageDrawable(dotsBlurImage);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            if (changeInterval == NONE_CHANGE_INTERVAL) {
                return;
            }
            if (isContinue) {
                pageHandler.sendEmptyMessage(position);
                position = (position + 1) % views.size();
                try {
                    if (changeInterval > MAX_CHANGE_INTERVAL 
                            || changeInterval < MIN_CHANGE_INTERVAL) {
                        changeInterval = DEFAULT_CHANGE_INTERVAL;
                    }
                    Thread.sleep(changeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ImageViewPagerAdapter extends PagerAdapter {

        private List<View> imageViews = null;
        private ScaleType scaleType;

        public ImageViewPagerAdapter(List<View> imageViews) {
            this(imageViews, ScaleType.CENTER);
        }

        public ImageViewPagerAdapter(List<View> imageViews, ScaleType scaleType) {
            super();
            this.imageViews = imageViews;
            this.scaleType = scaleType;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // set scale type
            View view = imageViews.get(position);
            ViewPager viewPager = (ViewPager) container;
            if (view instanceof ImageView){
                ((ImageView) view).setScaleType(scaleType);
            }
            viewPager.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
