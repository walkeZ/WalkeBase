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
 * 3.重绘invalidate()；60帧每秒
 */

public class RadarRippleView extends View {

    private Bitmap middleIcon = null;
    private int mWidth;
    private int mHeight;
    private int middleIconId = R.mipmap.ic_launcher_round;
    private float mRadius = 50;//圆弧半径
    private float mMaxRadius = 900;//最大圆弧半径
    private Paint mPaint;

    public RadarRippleView(Context context) {
        this(context, null);
    }

    public RadarRippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RadarRippleView, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.RadarRippleView_middleIcon)
                middleIconId = ta.getResourceId(index, R.mipmap.ic_launcher_round);

        }
        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除锯齿

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mMaxRadius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float cx = ((float) mWidth) / 2;
        float xy = ((float) mHeight) / 2;
        mPaint.setColor(Color.RED);
        canvas.drawCircle(cx, xy, mRadius, mPaint);

        Log.i("walke", "onDraw: ---------------");

        if (middleIcon == null)
            middleIcon = BitmapFactory.decodeResource(getResources(), middleIconId);
        int iconHeight = middleIcon.getHeight();
        int iconWidth = middleIcon.getWidth();
        int x = (mWidth - iconWidth) / 2;//
        int y = (mHeight - iconHeight) / 2;

        canvas.drawBitmap(middleIcon, x, y, mPaint);//画图由左上角开始

        if (mRadius<mMaxRadius){
            mRadius+=3;
        }else {
            mRadius=50;
        }
        invalidate();
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
