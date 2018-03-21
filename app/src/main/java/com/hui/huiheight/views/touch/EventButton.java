package com.hui.huiheight.views.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by walke.Z on 2017/8/21.
 */

public class EventButton extends Button {
    private static final String TAG = "Event";

    public EventButton(Context context) {
        super(context);
    }

    public EventButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "--------------- EventButton -----------------onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "--------------- EventButton -----------------onTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "--------------- EventButton -----------------dispatchTouchEvent: "+b);
        return b;
    }


    /*@Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i(TAG, "--------------- EventButton -----------------onInterceptHoverEvent: ");
        return super.onInterceptHoverEvent(event);
    }*/
}
