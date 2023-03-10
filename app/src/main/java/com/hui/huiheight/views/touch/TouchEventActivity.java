package com.hui.huiheight.views.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hui.huiheight.R;

import walke.base.tool.ToastUtil;

public class TouchEventActivity extends AppCompatActivity {

    private static final String TAG = "Event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    public void toasting(View view) {
        ToastUtil.showToast(this,"TouchEventActivity");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.i(TAG, "--------------- TouchEventActivity -----------------onTouchEvent: "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "--------------- TouchEventActivity -----------------dispatchTouchEvent: "+b);
        return b;
    }

}
