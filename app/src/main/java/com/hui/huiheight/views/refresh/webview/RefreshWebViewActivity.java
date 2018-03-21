package com.hui.huiheight.views.refresh.webview;

import android.graphics.Color;
import android.os.Handler;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hui.huiheight.R;

import com.hui.huiheight.ButterKnifeActivity;

/**
 * Created by walke.Z on 2017/8/14.
 */

public class RefreshWebViewActivity extends ButterKnifeActivity {


   /* @BindView(R.index.arw_webView)
    RefreshWebView mWebView;
    @BindView(R.index.arw_RefreshWebViewLinearLayout)
    RefreshWebViewLinearLayout mRefreshWebViewLinearLayout;*/

    RefreshWebView mWebView;
    RefreshWebViewLinearLayout mRefreshWebViewLinearLayout;

    @Override
    public int layoutId() {
        return R.layout.activity_refresh_webview;
    }

    @Override
    protected void initData() {
        mWebView = (RefreshWebView) findViewById(R.id.arw_webView);
        TextView tvTitle = (TextView) findViewById(R.id.arw_title);
        tvTitle.setText("title:RefreshWebViewActivity");
        mRefreshWebViewLinearLayout = (RefreshWebViewLinearLayout) findViewById(R.id.arw_RefreshWebViewLinearLayout);
        initWebView();
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
        mRefreshWebViewLinearLayout.setRefreshListener(new RefreshWebViewLinearLayout.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshWebViewLinearLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

    }



}
