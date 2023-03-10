package com.hui.huiheight.views.refresh.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hui.huiheight.R;

import walke.base.tool.ViewUtil;
import walke.base.tool.WindowUtil;


/**
 * Created by walke.Z on 2017/8/14.
 * 最终版
 */

public class RefreshWebViewLayout extends LinearLayout {
    private static final String TAG = "RefreshScrollLinearLayout2";
    private Scroller scroller;
    OnRefreshListener mRefreshListener;
    private float scrollSpead = 2.5f;
    float downY;
    private int scrollStart;
    private int scrollEnd;
    private int headerHeight;
    public final static int  STATE_ORIGINAL = 0;
    public final static int STATE_MOVE = 1;
    public final static int STATE_REFRESHING = 2;
    private int mState = STATE_ORIGINAL;
    private View headerView;
    private VelocityTracker velocityTracker;
    private int childCount;
    private int contentHeight;
    private int interval;
    private TextView tvText;

    private boolean isRefreshing;


    public RefreshWebViewLayout(Context context) {
        this(context, null);
    }

    public RefreshWebViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshWebViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        headerView = View.inflate(context, R.layout.refresh_header, null);
        tvText = ((TextView) headerView.findViewById(R.id.rh_text));
        this.addView(headerView, 0);
        headerHeight = ViewUtil.getViewHeight(headerView);
        int screenHeight = WindowUtil.getWindowHeight(context);
        interval = 0;
        scroller.startScroll(0, 0, 0, headerHeight);
        contentHeight = screenHeight - headerHeight;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       /* if (changed) {
            for (int i = 0; i < childCount; i++) {
                if (i == 1) {
                    View childView = getChildAt(i);
                    childView.layout(l, headerHeight, r, contentHeight);

                    LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                    layoutParams.height = contentHeight;
                    childView.setLayoutParams(layoutParams);
                }
            }
        } else {
            super.onLayout(changed, l, t, r, b);
        }*/
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec+headerHeight);
        /*childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 1) {
                View childView = getChildAt(i);
                int heightSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
                measureChild(childView, widthMeasureSpec, heightSpec);
            }
        }*/
    }
    /**
     * ViewGroup 事件分发中一定会走的方法  ，当子View的dispatchTouchEvent()方法[该方法一定会走]，
     * 子View返回false时走，返回true跳过ViewGroup的onTouchEvent()方法直接走dispatchTouchEvent()方法
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * ViewGroup 事件分发中一定会走的方法
     * @param ev
     * @return
     */
    @SuppressLint("LongLogTag")
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);

        float eventY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollStart = getScrollY();
                downY = eventY;
                Log.i(TAG, "onInterceptTouchEvent: ------------->downY=" + downY);
                break;

            case MotionEvent.ACTION_MOVE:
                float v = eventY - downY;
                Log.i(TAG, "onInterceptTouchEvent: ------------->v=" + v);
                //if (v > interval && ((BaseActivity) getContext()).getTestApp().isWebViewTop()) {//下拉
                if (v > interval && RefreshWebView.isTop) {//下拉
                    if (v>headerHeight&&v<headerHeight*2){
                        setState(STATE_ORIGINAL);
                    }else if (v>headerHeight*2){
                        setState(STATE_MOVE);
                    }
                    scrollTo(0, (int) ((int) scrollStart - v / scrollSpead));
                } else {

                }

                break;

            case MotionEvent.ACTION_UP:

                velocityTracker.computeCurrentVelocity(1000); //设置units的值为1000，意思为一秒时间内运动了多少个像素
                float yVelocity = velocityTracker.getYVelocity();
                float moveY = eventY - downY;
                scrollEnd = getScrollY();
                //if (moveY > headerHeight && ((BaseActivity) getContext()).getTestApp().isWebViewTop()) {//下拉
                if (moveY > headerHeight && RefreshWebView.isTop) {//下拉
                    scroller.startScroll(0, scrollEnd, 0, -scrollEnd);//+headerHeight
                    setState(STATE_REFRESHING);
                } else {
                    scroller.startScroll(0, scrollEnd, 0, -scrollEnd + headerHeight);//+headerHeight
                    setState(STATE_ORIGINAL);
                }
                postInvalidate();
                defaultDelayedComplete(2000);//++
                Log.i(TAG, "onInterceptTouchEvent: ACTION_UP------------> eventY = " + eventY + " -----> yVelocity = " + yVelocity);
                Log.i(TAG, "onInterceptTouchEvent: ACTION_UP------------> moveY = " + moveY + " -----> getScrollY = " + getScrollY() + " \n");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void defaultDelayedComplete(int time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshComplete();
            }
        },time);
    }

    public void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_MOVE:
                tvText.setText("松开以刷新");
                //isRefreshing=false;
                break;
            case STATE_ORIGINAL:
                tvText.setText("下拉刷新");
                //isRefreshing=false;
                break;
            case STATE_REFRESHING:
                if (null != mRefreshListener) {
                    mRefreshListener.onRefreshing();
                    tvText.setText("正在刷新...");
                    isRefreshing=true;
                }
                break;
            default:
        }

        mState = state;
    }

    public void refreshComplete() {
        if (isRefreshing) {
            boolean b = scroller.computeScrollOffset();
            scrollTo(0, headerHeight);
            postInvalidate();
            setState(STATE_ORIGINAL);
        }
    }



    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public interface OnRefreshListener {
        void onRefreshing();
    }
}
