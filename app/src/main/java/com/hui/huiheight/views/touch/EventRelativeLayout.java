package com.hui.huiheight.views.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * Created by walke.Z on 2017/8/21.
 */

public class EventRelativeLayout extends RelativeLayout {
    private static final String TAG = "Event";

    public EventRelativeLayout(Context context) {
        super(context);
    }

    public EventRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "--------------- EventRelativeLayout -----------------onInterceptTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "--------------- EventRelativeLayout -----------------onTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "---------------- EventRelativeLayout ----------------dispatchTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean b = super.onInterceptHoverEvent(event);
        Log.i(TAG, "---------------- EventLinearLayout ----------------onInterceptHoverEvent: "+b);
        return b;
    }
}
