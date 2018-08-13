package walke.widget.xiu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import walke.widget.R;

/**
 * Created by walke.Z on 2018/3/23.
 * 参考：【Android进阶】如何写一个很屌的动画（1）：http://lib.csdn.net/article/android/57005?knId=634
 * <p>
 * 实现支付宝咻一咻动画效果：雷达波纹扩散效果
 * 分析：动画实现postInvalidate();或者 invalidate();
 * 1.中间一个圆形图片,  实现点canvas.drawBitmap, 发现
 * a.获取控件的宽高用于定位中点，建议在onAttachedToWindow()通过getWidth、getHeight获取
 * b.在控件中间换一个圆形图
 * 2.点击一次创建一个圆，并动画：由小变大,实现点canvas.drawBitmap动态改变半径
 *  a.先实现一个圆的放大动画：canvas.drawBitmap动态改变radius ，radius具有最大值mRadius与最小值mMaxRadius,+一个speed
 *  b.用数组表示初始半径，然后在onDraw方法遍历画圆:/发现无法做到由中心一直间隔同个距离扩散，应该用list集合，当每超过一个间隔，就添加一个
 * 3.重绘invalidate()；60帧每秒
 */

public class RadarRippleView3 extends View {

    private Bitmap middleIcon = null;
    private int mWidth;
    private int mHeight;
    private int middleIconId = R.mipmap.ic_launcher_round;
    private float mMaxRadius = 900;//最大圆弧半径
    private float mMinRadius = 20;//最大圆弧半径

    private float mRadius = mMinRadius;//圆弧半径

//    private float[] radiusTag=new float[]{40,50,60,70,80};
    private List<Float> radiusList =new ArrayList<>();//发现无法做到由中心一直间隔同个距离扩散，应该用list集合，当每超过一个间隔，就添加一个
    private int interval=20;
    private float[] radiusOrgin=new float[]{40,50,60};
    private int[] alphaArray=new int[]{255,135,85};

    private Paint mPaint;
    private float mSpeed = 2f;

    public RadarRippleView3(Context context) {
        this(context, null);
    }

    public RadarRippleView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarRippleView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RadarRippleView2, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.RadarRippleView2_middleIcon)
                middleIconId = ta.getResourceId(index, R.mipmap.ic_launcher_round);

        }
        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除锯齿

        radiusList.add(mMinRadius);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mMaxRadius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;//半径最大值
        Log.i("walke", "onSizeChanged: ---------- w = " + w + "--- h = " + h);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        int width = getWidth();
        int height = getHeight();
        Log.i("walke", "onAttachedToWindow: --------------------- width = " + width + " ---------- height = " + height);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int width = getWidth();
        int height = getHeight();
        Log.i("walke", "onAttachedToWindow: --------------------- width = " + width + " ---------- height = " + height);
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("walke", "onDraw: ---------------");
//        invalidate();
        float cx = ((float) mWidth) / 2;
        float xy = ((float) mHeight) / 2;
        mPaint.setColor(Color.RED);

        for (int i = 0; i < radiusList.size(); i++) {
            if (radiusList.get(i)<mMaxRadius) {

                int alpha = (int) (255.0F * (1.0F - (radiusList.get(i)) * 1.0f / mMaxRadius));//
                Log.i("walke", "onDraw: ---------------- alpha = "+alpha);
                mPaint.setAlpha(alpha);

                canvas.drawCircle(cx, xy, radiusList.get(i), mPaint);
                radiusList.set(i, radiusList.get(i) + mSpeed);


            }else {

            }
        }

        // 判断当波浪圆扩散到指定宽度时添加新扩散圆,即当一个圈由小到指定宽度后，添加一个圈[透明度：不透明，半径为0]
//        if (radiusList.get(radiusList.size()-1)==interval) {
        if (Math.abs(radiusList.get(radiusList.size()-1)-mMinRadius-interval)<=mSpeed) {
            radiusList.add(mMinRadius);
//            mPaint.setAlpha(155);
        }


        drawIcon(canvas);
        invalidate();


    }

    private void drawIcon(Canvas canvas) {
        if (middleIcon == null)
            middleIcon = BitmapFactory.decodeResource(getResources(), middleIconId);
        int iconHeight = middleIcon.getHeight();
        int iconWidth = middleIcon.getWidth();
        int x = (mWidth - iconWidth) / 2;//
        int y = (mHeight - iconHeight) / 2;
        mPaint.setAlpha(255);
        canvas.drawBitmap(middleIcon, x, y, mPaint);//画图由左上角开始
    }

    /**
     * 跳转界面后会自动停
     */
    @Override
    public void invalidate() {
        super.invalidate();
        Log.i("walke", "invalidate: -------------------------------RadarRippleView");
    }

    /**
     * 跳转界面后估计不会自动停
     */
    @Override
    public void postInvalidate() {
        super.postInvalidate();
        Log.i("walke", "postInvalidate: -------------------------------RadarRippleView");
    }
}
