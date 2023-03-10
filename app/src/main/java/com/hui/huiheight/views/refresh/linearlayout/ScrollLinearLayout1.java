package com.hui.huiheight.views.refresh.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * Created by walke.Z on 2017/8/14.
 * 类似ScrollView只有一个子View(容器)
 */

public class ScrollLinearLayout1 extends LinearLayout {
    private float scrollSpead=2.5f;
    private Scroller scroller;
    float downY;
    private int childCount;
    private int screenHeight;
    private int scrollStart;
    private int scrollEnd;
    private int currentPage;
    private VelocityTracker velocityTracker;

    public ScrollLinearLayout1(Context context) {
        this(context,null);
    }

    public ScrollLinearLayout1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollLinearLayout1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        scroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(l, i * screenHeight, r, (i + 1) * screenHeight);
                LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                layoutParams.height = screenHeight;
                childView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int heightSpec = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.EXACTLY);
            measureChild(childView, widthMeasureSpec, heightSpec);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float eventY = ev.getY();

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollStart = getScrollY();
                downY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //scrollTo(0, (int) ((int) scrollStart - (eventY - downY)));
                scrollTo(0, (int) ((int) scrollStart - (eventY - downY)/scrollSpead));
                break;
            case MotionEvent.ACTION_UP:
                scrollEnd = getScrollY();
                currentPage=0;
                scroller.startScroll(0, scrollEnd, 0, screenHeight * currentPage - scrollEnd);
                postInvalidate();
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        return true;//super.onInterceptTouchEvent(ev)
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
