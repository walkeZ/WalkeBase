package com.hui.huiheight.first;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.tool.LayoutParamsUtil;
import walke.base.tool.ViewUtil;
import walke.base.tool.WindowUtil;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2018/1/26.
 */

public class ShopCarActivity extends TitleActivity {
    private ImageView mIv,mIv2,mIv3,mIv4;
    private Button mBt,mBt2;
    private TextView mTv1,mTv2,mTv3,mTv4;
    private LinearLayout rightLayout;
    private boolean isShowing =false;//是否显示着
    private ImageView mBall2;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("ShopCarActivity");

        rl = ((RelativeLayout) findViewById(R.id.shop_car_RelativeLayout));

        mIv = ((ImageView) findViewById(R.id.shop_car_iv1));
        mIv2 = ((ImageView) findViewById(R.id.shop_car_iv2));
        mIv3 = ((ImageView) findViewById(R.id.shop_car_iv3));
        mIv4 = ((ImageView) findViewById(R.id.shop_car_iv4));
        mBt = ((Button) findViewById(R.id.shop_car_bt1));
        mBt2 = ((Button) findViewById(R.id.shop_car_bt2));

        rightLayout = ((LinearLayout) findViewById(R.id.shop_car_rightLayout));
        rightLayout.setVisibility(View.INVISIBLE);
        mTv1 = ((TextView) findViewById(R.id.shop_car_tv1));
        mTv2 = ((TextView) findViewById(R.id.shop_car_tv2));
        mTv3 = ((TextView) findViewById(R.id.shop_car_tv3));
        mTv3 = ((TextView) findViewById(R.id.shop_car_tv3));
//
//        mTv4 =new TextView(this);
//        mTv4.setText("mTv4");
//        mTv4.setLayoutParams(LayoutParamsUtil.getL_P_MV(50));
//        mTv4.setGravity(Gravity.CENTER);
//        mTv4.setTextSize(35);
//        mTv4.setBackgroundColor(Color.RED);
//        rightLayout.addView(mTv4);

        mIv.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);

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
            case R.id.shop_car_iv2:
                addCart(mIv2,800);
                setAnim2(v,350,500);
                mTv4 =new TextView(this);
                mTv4.setText("mTv4");
                mTv4.setLayoutParams(LayoutParamsUtil.getL_P_MV(60));
                mTv4.setGravity(Gravity.CENTER);
                mTv4.setTextSize(35);
                mTv4.setBackgroundColor(Color.RED);
                rightLayout.addView(mTv4);
//                fly();
                break;
            case R.id.shop_car_iv3:
//

                ShopCarUtil.setAnim(ShopCarActivity.this,v,mTv4,350,550);
                break;
            case R.id.shop_car_iv4:
//                setAnim(v,300,500);// 350,600 效果可以
                ShopCarUtil.setAnim(ShopCarActivity.this,v,mTv1,350,550);
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

    private void rightEixt() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_exit);
        animation.setFillAfter(true);
        rightLayout.startAnimation(animation);
        rightLayout.setVisibility(View.INVISIBLE);
    }


    private void fly() {
        //true表示动画的插值器统一使用AnimationSet默认的插值器
        AnimationSet set = new AnimationSet(true);
//        TranslateAnimation ta = new TranslateAnimation(0, mIv.getWidth(), 0, mIv.getHeight());
        TranslateAnimation ta = new TranslateAnimation(0, WindowUtil.getWindowWidth(this)+3500, 0, 0);
        ta.setDuration(600);
        //将所有动画添加到set中
        set.addAnimation(ta);
        ScaleAnimation aa = new ScaleAnimation(1f,0.2f,1f,0.2f,ViewUtil.getViewWidth(mIv)/2,ViewUtil.getViewHeight(mIv)/2);
        aa.setDuration(200);
        set.addAnimation(aa);
//        ScaleAnimation sa = new ScaleAnimation(1, 2, 1, 2, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//        sa.setDuration(3000);
//        set.addAnimation(sa);
//        RotateAnimation ra = new RotateAnimation(0, -359, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        ra.setDuration(3000);
//        set.addAnimation(ra);
        mIv.startAnimation(set);
        set.setFillAfter(true);
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

    public void setAnim2(final View v,int scaleTime,int flyTime) {

        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        int width = v.getWidth();
        int height = v.getHeight();
        mBall2 = new ImageView(this);
        int wValue = ViewUtil.dpToPx(this, width);
        int hValue = ViewUtil.dpToPx(this, height);
        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(wValue, hValue);
//        LinearLayout.LayoutParams lp = LayoutParamsUtil.getL_P_VV(400, 400);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(500,500);//  mBall2.getLayoutParams() == null;
//        lp.width=400;
//        lp.height=400;
        mBall2 = new ImageView(this);
        mBall2.setLayoutParams(lp);
        mBall2.setImageResource(R.mipmap.scan_buy_2x108);
//        mBall2.setScaleType(ImageView.ScaleType.FIT_XY);

        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(mBall2);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, mBall2, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        mTv3.getLocationInWindow(endLocation);
        // 计算位移
//        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endX = endLocation[0] - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1]- ((int) (hValue * 0.3));// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
//        translateAnimationX.setInterpolator(new DecelerateInterpolator());
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new DecelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);


        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
//        set.addAnimation(sa);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(flyTime);// 动画的执行时间
//        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                mBall2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                mBall2.setVisibility(View.GONE);
            }
        });


        //缩放中心点参考ImageView的中心点，0.5f表示宽度/高度的50%
//        ScaleAnimation sa = new ScaleAnimation(1, 0.2f, 1, 0.2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//        sa.setDuration(600);
//        sa.setFillAfter(true);
//        view.startAnimation(sa);
//        sa.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.startAnimation(set);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.3f);
        scaleXAnimator.setDuration(scaleTime);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.3f);
        scaleYAnimator.setDuration(scaleTime);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();

//        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f);
//        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.3f);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder);
//        objectAnimator.setDuration(500);
//        objectAnimator.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.startAnimation(set);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

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
//        animLayout.setBackgroundColor(Color.parseColor("#33e2ee22"));
        rootView.addView(animLayout);
        return animLayout;
    }


    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private RelativeLayout rl;
    private PathMeasure mPathMeasure;
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];

    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     * @param iv
     */
    private void addCart( ImageView iv,int time) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(this);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        rl.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        mTv2.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
//        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
//        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
//        float startY = startLoc[1] - parentLocation[1] - iv.getHeight();
        float startY = startLoc[1] - parentLocation[1] +iv.getHeight()/2;

        //商品掉落后的终点坐标：商品起始点-父布局起始点+购物车图片的1/5
//        float toX = endLoc[0] - parentLocation[0] + cart.getWidth() / 5;
//        float toY = endLoc[1] - parentLocation[1];
//        float toX = endLoc[0] - startX+mTv2.getWidth() / 2;//date：2018.01.30
//        float toY = endLoc[1] - startY+mTv2.getHeight() / 2;

        float toX = endLoc[0] - startX+mTv2.getWidth() / 2;//date：2018.01.31
        float toY = endLoc[1] - startY;

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
//        path.quadTo((startX + toX) / 2, startY, toX, toY);
        path.quadTo((startX + toX/1.5f) , startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(time);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 把移动的图片imageview从父布局里移除
                rl.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
