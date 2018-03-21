package com.hui.huiheight.views.refresh.linearlayout;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class RefreshLinearLayoutActivity4 extends TitleActivity {
    private WebView mWebView;
    private ScrollLinearLayout4 mScrollLinearLayout4;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_linearlayout4;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RefreshLinearLayoutActivity4");
        mWebView = ((WebView) findViewById(R.id.arl4_webView));
        mScrollLinearLayout4 = ((ScrollLinearLayout4) findViewById(R.id.arl4_scrollLinearLayout4));
        initWebView();
    }

    @Override
    protected void initData() {

    }
    private void initWebView() {
        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //防止跳转到系统自带的浏览器中打开
        mWebView.setWebViewClient(new WebViewClient());

        //载入js
        mWebView.loadUrl("http://www.baidu.com/");
        mScrollLinearLayout4.setRefreshListener(new ScrollLinearLayout4.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollLinearLayout4.refreshComplete();
                    }
                },2000);
            }
        });

    }

    public void click(View v){
        toast("click");
    }
    public void toasting(View v){
        toast("RefreshLinearLayoutActivity3");
    }
}
