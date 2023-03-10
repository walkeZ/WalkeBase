package com.hui.huiheight.views.refresh.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;

import walke.base.tool.ViewUtil;
import walke.base.tool.WindowUtil;


/**
 * Created by walke.Z on 2017/8/14.
 * 类似ScrollView只有一个子View(容器)
 */

public class ScrollLinearLayout3 extends LinearLayout {
    private static final String TAG = "ScrollLinearLayout3";
    private float scrollSpead = 2.5f;
    private Scroller scroller;
    float downY;
    private int childCount;
    private int scrollStart;
    private int scrollEnd;
    private LinearLayout mHeaderView;
    private ProgressBar pBar;
    private TextView tvTips;
    private int headerHeight;
    private int windowHeight;

    public ScrollLinearLayout3(Context context) {
        this(context, null);
    }

    public ScrollLinearLayout3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLinearLayout3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        mHeaderView = new LinearLayout(context);
        pBar = new ProgressBar(context);
        tvTips = new TextView(context);
        tvTips.setTextSize(17);
        tvTips.setText("正在刷新...");
        //tvTips.setBackgroundColor(Color.RED);
        tvTips.setGravity(Gravity.CENTER_VERTICAL);
        //mHeaderView.setLayoutParams(LayoutParamsUtil.getL_P_MV(ViewTools.dipTopx(context, 50)));
        mHeaderView.setGravity(Gravity.CENTER);
        //mHeaderView.setBackgroundColor(Color.RED);
        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewTools.dipTopx(context, 50));
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewTools.dipTopx(context, 30));
        mHeaderView.addView(tvTips,params);
        mHeaderView.addView(pBar,params2);*/
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewUtil.dpToPx(context, 30));
        params2.rightMargin= ViewUtil.dpToPx(context,6);
        mHeaderView.addView(pBar,params2);
        mHeaderView.addView(tvTips);
        this.addView(mHeaderView, 0);
        windowHeight = WindowUtil.getWindowHeight(context);
        //headerHeight = ViewTools.getViewHeight(mHeaderView);
        headerHeight = ViewUtil.dpToPx(context, 50);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            for (int i = 0; i < childCount; i++) {
                if (i == 0) {
                    View childView = getChildAt(i);
                    childView.layout(l, 0, r, headerHeight);
                    LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                    layoutParams.height = headerHeight;
                    childView.setLayoutParams(layoutParams);
                }else if (i == 1) {
                    View childView = getChildAt(i);
                    childView.layout(l, headerHeight, r, windowHeight);
                    LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                    layoutParams.height = windowHeight-headerHeight;
                    childView.setLayoutParams(layoutParams);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                View childView = getChildAt(i);
                int heightSpec = MeasureSpec.makeMeasureSpec(headerHeight, MeasureSpec.EXACTLY);
                measureChild(childView, widthMeasureSpec, heightSpec);
            }else if (i == 1) {
                View childView = getChildAt(i);
                int heightSpec = MeasureSpec.makeMeasureSpec(windowHeight-headerHeight, MeasureSpec.EXACTLY);
                measureChild(childView, widthMeasureSpec, heightSpec);
            }
        }
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


        boolean interceptEvent = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "-------onTouchEvent: onTouchEvent: " +onTouchEvent + ", interceptEvent = " + interceptEvent
                + ", (Down 0;Move 2;Up 1)； " + ev.getAction());
        return interceptEvent;//
    }

    /**
     * ViewGroup 事件分发中一定会走的方法  ，当子View的dispatchTouchEvent()方法[该方法一定会走]，
     * 子View返回false时走，返回true跳过ViewGroup的onTouchEvent()方法直接走dispatchTouchEvent()方法
     *
     * @param ev
     * @return
     */
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//       /* Log.i(TAG, "---------onTouchEvent: ");
//        float eventY = ev.getY();
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
//                scroller.startScroll(0, scrollEnd, 0,  - scrollEnd);
//                break;
//        }*/
//        boolean interceptEvent = super.onInterceptTouchEvent(ev);
//        Log.i(TAG, "---------onTouchEvent: interceptEvent " + interceptEvent);
//        return interceptEvent;
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
                scrollTo(0, (int) ((int) scrollStart - (eventY - downY) / scrollSpead));
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
