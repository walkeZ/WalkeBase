package com.hui.huiheight.first;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2018/1/26.
 */

public class ShopCar2Activity extends TitleActivity {
    private ImageView mIv;
    private Button mBt,mBt2;
    private TextView mTv1,mTv2,mTv3;
    private LinearLayout rightLayout;
    private boolean isShowing =false;//是否显示着

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_shop_car2;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("ShopCarActivity");
        mIv = ((ImageView) findViewById(R.id.shop_car2_iv1));
        mBt = ((Button) findViewById(R.id.shop_car2_bt1));
        mBt2 = ((Button) findViewById(R.id.shop_car2_bt2));
        rightLayout = ((LinearLayout) findViewById(R.id.shop_car2_rightLayout));
        rightLayout.setVisibility(View.INVISIBLE);
        mTv1 = ((TextView) findViewById(R.id.shop_car2_tv1));
        mTv2 = ((TextView) findViewById(R.id.shop_car2_tv2));
        mTv3 = ((TextView) findViewById(R.id.shop_car2_tv3));

        mIv.setOnClickListener(this);
        mBt.setOnClickListener(this);
        mBt2.setOnClickListener(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shop_car_iv1:
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(this);
                ball.setImageResource(R.drawable.icon_sun);
                setAnim(ball,startLocation);
//                fly();
                break;
            case R.id.shop_car_bt1:
                rightEnter();
                break;
            case R.id.shop_car_bt2:
                rightEnter();
                break;
            case R.id.shop_car_tv1:
                toast("text1");
                break;
            case R.id.shop_car_tv2:
                toast("text2");
                break;
            case R.id.shop_car_tv3:
                toast("text3");
//                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
//                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
//                setAnim(mIv,);
                break;
        }
    }


    private void rightEnter() {

        if (!isShowing) {

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_enter);
            animation.setFillAfter(true);
            rightLayout.startAnimation(animation);
            rightLayout.setVisibility(View.VISIBLE);
            isShowing =true;
        }else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_exit);
            animation.setFillAfter(true);
            rightLayout.startAnimation(animation);
            rightLayout.setVisibility(View.INVISIBLE);
            isShowing=false;
        }
    }


    private ViewGroup anim_mask_layout;//动画层

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        mTv3.getLocationInWindow(endLocation);
        // 计算位移
//        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endX = endLocation[0] - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(3000);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }


    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {

        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }


    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

}
