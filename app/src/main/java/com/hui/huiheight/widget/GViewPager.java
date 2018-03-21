package com.hui.huiheight.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by walke.Z on 2017/6/16.
 *
 */

public class GViewPager extends NoPreloadViewPager {

    public GViewPager(Context context) {
        super(context);
    }

    public GViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

}
