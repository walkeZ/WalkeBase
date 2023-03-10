package com.hui.huiheight.views.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by walke.Z on 2017/8/21.
 */

public class EventLinearLayout extends LinearLayout {
    private static final String TAG = "Event";

    public EventLinearLayout(Context context) {
        super(context);
    }

    public EventLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);

        Log.i(TAG, "--------------- EventLinearLayout -----------------onInterceptTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);

        Log.i(TAG, "--------------- EventLinearLayout -----------------onTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "---------------- EventLinearLayout ----------------dispatchTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean b = super.onInterceptHoverEvent(event);
        Log.i(TAG, "---------------- EventLinearLayout ----------------onInterceptHoverEvent: "+b);
        return b;
    }
}
