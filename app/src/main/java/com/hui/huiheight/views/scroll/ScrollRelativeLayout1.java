package com.hui.huiheight.views.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import walke.base.tool.WindowUtil;


/**
 * Created by walke.Z on 2022/05/09.
 * 类似ScrollView只有一个子View(容器)
 */

public class ScrollRelativeLayout1 extends RelativeLayout {
    private static final String TAG = "ScrollRelativeLayout1";
    private float scrollSpead = 2.5f;
    private Scroller scroller;
    float downY;
    private int scrollStart;
    private int scrollEnd;
    private int windowHeight;
    private VelocityTracker velocityTracker; // 控制执行此次，类型线程安全

    public ScrollRelativeLayout1(Context context) {
        this(context, null);
    }

    public ScrollRelativeLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRelativeLayout1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        windowHeight = WindowUtil.getWindowHeight(context);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {

        boolean hoverEvent = super.onInterceptHoverEvent(event);
        Log.i(TAG, "-----onInterceptHoverEvent: " + hoverEvent + ", (Down 0;Move 2;Up 1)； " + event.getAction());
        return hoverEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean cost = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "-------onInterceptTouchEvent: cost = " + cost);
        return cost;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean onTouchEvent = super.onTouchEvent(ev);
       
//        boolean interceptEvent = super.onInterceptTouchEvent(ev);
//        Log.i(TAG, "-------onTouchEvent: onTouchEvent: " +onTouchEvent + ", interceptEvent = " + interceptEvent
//                + ", (Down 0;Move 2;Up 1)； " + ev.getAction());

        return true;//
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        float eventY = ev.getY();
//
//        if (velocityTracker == null) {
//            velocityTracker = VelocityTracker.obtain();
//        }
//        velocityTracker.addMovement(ev);
//        Log.i(TAG, "-----------onTouchEvent: (Down 0;Move 2;Up 1)； " + ev.getAction());
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                scrollStart = getScrollY();
//                downY = eventY;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //scrollTo(0, (int) ((int) scrollStart - (eventY - downY)));
//                scrollTo(0, (int) ((int) scrollStart - (eventY - downY)/scrollSpead));
//                break;
//            case MotionEvent.ACTION_UP:
//                scrollEnd = getScrollY();
//                scroller.startScroll(0, scrollEnd, 0, - scrollEnd);
//                postInvalidate();
//                if (velocityTracker != null) {
//                    velocityTracker.recycle();
//                    velocityTracker = null;
//                }
//                break;
//        }
//        return true;//super.onInterceptTouchEvent(ev)
//    }

    /**
     * ViewGroup 事件分发中一定会走的方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "-----------dispatchTouchEvent:  (Down 0;Move 2;Up 1)； " + ev.getAction());
        float eventY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollStart = getScrollY();
                downY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //scrollTo(0, (int) ((int) scrollStart - (eventY - downY)));
                scrollTo(0, (int) (scrollStart - (eventY - downY) / scrollSpead));
                break;

            case MotionEvent.ACTION_UP:
                scrollEnd = getScrollY();
                scroller.startScroll(0, scrollEnd, 0, -scrollEnd);
                postInvalidate();

                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

}
