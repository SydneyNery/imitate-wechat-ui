package com.virtual.plan.tomatoplan.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virtual.plan.tomatoplan.R;
import com.virtual.plan.tomatoplan.constant.Constant;

/**
 * 包含图片和文本的控件上下排列，文本的颜色和图片颜色一致，用于fragment的底本指示
 * @author Irvin
 *
 */
public class ImageText extends LinearLayout {

    private Context mContext = null;
    private ImageView mImageView = null;
    private TextView mTextView = null;

    private final static int DEFAULT_IMAGE_WIDTH = 64;
    private final static int DEFAULT_IMAGE_HEIGHT = 64;

    private int CHECKED_COLOR = Constant.GLOBAL_CHECKED_COLOR;
    private int UNCHECKED_COLOR = Constant.GLOBAL_UNCHECKED_COLOR;

    public ImageText(Context context) {
        super(context);
        mContext = context;
    }

    public ImageText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parentView = inflater.inflate(R.layout.image_text_layout, this, true);
        mImageView = (ImageView) parentView.findViewById(R.id.image_image_text);
        mTextView = (TextView) parentView.findViewById(R.id.text_image_text);
    }

    public void setImage(int id) {
        if(mImageView != null) {
            mImageView.setImageResource(id);
            setImageSize(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
        }
    }

    public void setImageBackground(int id) {
        if(mImageView != null) {
            mImageView.setBackgroundResource(id);
        }
    }

    public void setText(int text) {
        if(mTextView != null) {
            mTextView.setText(text);
            mTextView.setTextColor(UNCHECKED_COLOR);
        }
    }

    public void setTextColor(int color) {
        if(mTextView != null) {
            mTextView.setTextColor(color);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 设置指示物的宽高
     * @param w Image的宽度
     * @param h Image的高度
     */
    private void setImageSize(int w, int h) {
        if(mImageView != null){
            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            params.width = w;
            params.height = h;
            mImageView.setLayoutParams(params);
        }
    }

    /**
     * 当前所指示的fragment被选中时指示物的背景图片和颜色
     * @param checkDrawableId 指定的图片
     */
    public void setChecked(int checkDrawableId) {
        if(mTextView != null){
            mTextView.setTextColor(CHECKED_COLOR);
        }

        if(mImageView != null){
            mImageView.setImageResource(checkDrawableId);
        }
    }

    public void setUnChecked(int checkDrawableId) {
        if(mTextView != null) {
            mTextView.setTextColor(UNCHECKED_COLOR);
        }

        if(mImageView != null) {
            mImageView.setImageResource(checkDrawableId);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        int ceil = (int) Math.ceil(255 * alpha);
        mImageView.setAlpha(ceil);
    }

}
