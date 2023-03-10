package com.hui.huiheight.views.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by walke.Z on 2017/8/21.
 */

public class EventView extends View {
    private static final String TAG = "Event";

    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "--------------- EventTextView -----------------onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "--------------- EventView -----------------onTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "--------------- EventView -----------------dispatchTouchEvent: "+b);
        return b;
    }

   /* @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i(TAG, "--------------- EventTextView -----------------onInterceptHoverEvent: ");
        return super.onInterceptHoverEvent(event);
    }*/
}
