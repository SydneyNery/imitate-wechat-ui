package com.virtual.plan.tomatoplan.constant;

import android.graphics.Color;

public class Constant {
    //Log 开关
    public static final boolean LOG_GLOBAL = false;
    //Fragment的标识
    public static final String FRAGMENT_FLAG_HOME = "flag_home"; 
    public static final String FRAGMENT_FLAG_DESTINATION = "flag_destination"; 
    public static final String FRAGMENT_FLAG_CUSTOMER = "flag_customer_page"; 

    //底部文本的颜色一般为企业色
    public static final int GLOBAL_CHECKED_COLOR = Color.rgb(152,135,227);//紫色#9887E3
    public static final int GLOBAL_UNCHECKED_COLOR = Color.rgb(181,181,181);//灰色#b5b5b5

    public interface BottomState {
        int HOME = 0;
        int DESTINATION = 1;
        int CUSTOMER = 2;

        int TOTAL_COUNT = 3;
        int DEFAULT = HOME;
    }

}
