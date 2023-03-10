package com.hui.huiheight.views.refresh.recyclerview;

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

public class ScrollRecyclerView extends LinearLayout {
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

    public final static int  STATE_ORIGINAL = 0;
    public final static int STATE_MOVE = 1;
    public final static int STATE_REFRESHING = 2;
    private int mState = STATE_ORIGINAL;
    private int contentHeight;

    public ScrollRecyclerView(Context context) {
        this(context, null);
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewUtil.dpToPx(context, 30));
        params2.rightMargin=ViewUtil.dpToPx(context,6);
        mHeaderView.addView(pBar,params2);
        mHeaderView.addView(tvTips);
        this.addView(mHeaderView, 0);
        windowHeight = WindowUtil.getWindowHeight(context);
        //headerHeight = ViewTools.getViewHeight(mHeaderView);
        headerHeight = ViewUtil.dpToPx(context, 50);
        scroller.startScroll(0, 0, 0, headerHeight);
        int statusBarHeight = WindowUtil.getStatusBarHeight(context);
        //contentHeight = windowHeight-headerHeight+statusBarHeight;
        contentHeight = windowHeight-statusBarHeight;

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
                    childView.layout(l, headerHeight, r, contentHeight);
                    LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();

                    layoutParams.height = contentHeight;
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
                int heightSpec = MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.EXACTLY);
                measureChild(childView, widthMeasureSpec, heightSpec);
            }
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i(TAG, "-----onInterceptHoverEvent: ");
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "-------onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * ViewGroup 事件分发中一定会走的方法  ，当子View的dispatchTouchEvent()方法[该方法一定会走]，
     * 子View返回false时走，返回true跳过ViewGroup的onTouchEvent()方法直接走dispatchTouchEvent()方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * ViewGroup 事件分发中一定会走的方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "-----------dispatchTouchEvent: ");
        float eventY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollStart = getScrollY();
                downY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                float v = eventY - downY;
                if (v > 10 && CustomRecyclerView.isTop) {//下拉
                    scrollTo(0, (int) ((int) scrollStart - (eventY - downY) / scrollSpead));
                }else {
                    if (v>headerHeight&&v<headerHeight*2){
                        setState(STATE_ORIGINAL);
                    }else if (v>headerHeight*2){
                        setState(STATE_MOVE);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                float moveY = eventY - downY;
                scrollEnd = getScrollY();
                if (moveY > headerHeight && CustomRecyclerView.isTop) {//下拉
                    scroller.startScroll(0, scrollEnd, 0, -scrollEnd);//+headerHeight
                    setState(STATE_REFRESHING);
                } else {
                    scroller.startScroll(0, scrollEnd, 0, -scrollEnd + headerHeight);//+headerHeight
                    setState(STATE_ORIGINAL);
                }
                postInvalidate();

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_MOVE:
                tvTips.setText("松开以刷新");
                break;
            case STATE_ORIGINAL:
                tvTips.setText("下拉刷新");
                break;
            case STATE_REFRESHING:
                if (null != mOnRefreshListener) {
                    mOnRefreshListener.onRefreshing();
                    tvTips.setText("正在刷新...");
                }
                break;
            default:
        }

        mState = state;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

    public void refreshComplete() {
        boolean b = scroller.computeScrollOffset();
        scrollTo(0, headerHeight);
        postInvalidate();
        setState(STATE_ORIGINAL);

    }

    private OnRefreshListener mOnRefreshListener;

    public void setRefreshListener(OnRefreshListener refreshListener) {
        mOnRefreshListener = refreshListener;
    }

    public interface OnRefreshListener {
        void onRefreshing();
    }

}
