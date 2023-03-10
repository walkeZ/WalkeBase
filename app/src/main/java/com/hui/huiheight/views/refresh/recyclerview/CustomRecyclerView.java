package com.hui.huiheight.views.refresh.recyclerview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by walke.Z on 2017/8/14.
 */

public class CustomRecyclerView extends RecyclerView {

    private static final String TAG = "MyWebView";
    private Context mContext;

    public static boolean isTop;


    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //((BaseActivity) mContext).getTestApp().setWebViewTop(true);
        isTop = true;
        //去掉ListView 上下边界蓝色或黄色阴影
        if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrolled(int dx, int dy) {

        if (!canScrollVertically(-1)) {
            //顶部
            //onScrolledToTop();
            isTop = true;
        } else {
            isTop = false;
        }
        if (!canScrollVertically(1)) {
            //底部
            //onScrolledToBottom();
        } else if (dy < 0) {
            //onScrolledUp();
        } else if (dy > 0) {
            //onScrolledDown();
        }

        super.onScrolled(dx, dy);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        b = true;
        return b;
    }


}
