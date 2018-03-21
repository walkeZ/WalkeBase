package com.hui.huiheight.views.refresh.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;


/**
 * Created by walke.Z on 2017/8/14.
 */

public class CustomWebView extends WebView {

    private static final String TAG = "MyWebView";
    private Context mContext;

    public static boolean isTop;


    public CustomWebView(Context context) {
        this(context,null);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        //((BaseActivity) mContext).getTestApp().setWebViewTop(true);
        isTop=true;
    }

    //l,t代表left,top，也就是触摸点相对左上角的偏移量。而oldl,oldt就是滑动前的偏移量。
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("showBug", "-------->l= "+l+"  t= "+t+"  oldl= "+oldl+"  oldt= "+oldt);
        View childAt = this.getChildAt(0);
        //((BaseActivity) mContext).getTestApp().setWebViewTop(false);
        isTop=false;
        if (childAt != null && childAt.getMeasuredHeight() <= this.getScrollY() + this.getHeight() ) {

            Log.i("showBug", "-------->onScrollChanged: 到底部了");
        } else if (this.getScrollY() == 0) {
            Log.i("showBug", "-------->onScrollChanged: 顶部");
            isTop=true;
            //((BaseActivity) mContext).getTestApp().setWebViewTop(true);
        }else {

        }
        Log.i("showBug", "-------->onScrollChanged: isTop = "+isTop);
        super.onScrollChanged(l,t,oldl,oldt);
    }

}
