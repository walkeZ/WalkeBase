package com.hui.huiheight.views.refresh.linearlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by walke.Z on 2017/8/21.
 */

public class CustomLinearLayout3 extends LinearLayout{
    public CustomLinearLayout3(Context context) {
        super(context);
    }

    public CustomLinearLayout3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        // 如果直接return true，子控件获取不到事件，如果先super.dispatchTouchEvent(ev)后，return ture,子控件可以获取到事件
        b=true;
        return true;
//        return true;
    }
}
