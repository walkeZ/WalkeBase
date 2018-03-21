package walke.viewlibrary.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import walke.viewlibrary.R;

/**
 * Created by walke.Z on 2018/2/28.
 */

public class StarView2 extends View {
    private Bitmap mStarBitmap;
    private Paint mPaint = new Paint();
    private int mX1, mX2, mX3, mX4, mX5;
    private int mY1, mY2, mY3, mY4, mY5;
    private int mWidth;
    private int mHeight;
    private int mSpeed = 4;
    private int mSpeedY = mSpeed + 3;
    private boolean isDown = true;
    private int centerX;
    private int centerY;

    public StarView2(Context context) {
        this(context, null);
    }

    public StarView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStarBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.star);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("walke", "StarView2--onMeasure: ---------widthMeasureSpec=" + widthMeasureSpec + "    heightMeasureSpec=" + heightMeasureSpec);
        Log.i("walke", "StarView2--onMeasure: ---------getWidth=" + getWidth() + "    getHeight=" + getHeight());
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
//        mWidth = 500;
//        mHeight = 500;
        centerX = mWidth / 2 - mStarBitmap.getWidth() / 2;
        centerY = mHeight / 2 - mStarBitmap.getHeight() / 2;
        mX1 = mX2 = mX3 = mX4 = mX5 = centerX;
        mY1 = mY2 = mY3 = mY4 = mY5 = centerY;
        Log.i("walke", "StarView2--onSizeChanged: -----------w=" + w + "    h=" + h + "    oldw=" + oldw + "    oldh=" + oldh);
        Log.i("walke", "StarView2--onSizeChanged: -----------getWidth=" + getWidth() + "    getHeight=" + getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAlpha(255);//0~255
        if (mX1 > 0 || mY1 > 0) {
            mX1 -= 4;//4
            mY1 -= 8;
        } else {
            mX1 = centerX;
            mY1 = centerY;
        }
        canvas.drawBitmap(mStarBitmap, mX1, mY1, mPaint);
        if (mX2 < mWidth || mY2 > 0) {
            mX2 += 8;//4
            mY2 -= 8;
        } else {
            mX2 = centerX;
            mY2 = centerY;
        }
        canvas.drawBitmap(mStarBitmap, mX2, mY2, mPaint);

        if (mX3 < mWidth || mY3 < mHeight) {
            mX3 += 8;//4
            mY3 += 1;
        } else {
            mX3 = centerX;
            mY3 = centerY;
        }
        canvas.drawBitmap(mStarBitmap, mX3, mY3, mPaint);

        if (mX4 < mWidth || mY4 < mHeight) {
            mX4 += 3;//4
            mY4 += 8;
        } else {
            mX4 = centerX;
            mY4 = centerY;
        }
        canvas.drawBitmap(mStarBitmap, mX4, mY4, mPaint);

        if (mX5 > 0 || mY5 < mHeight) {
            mX5 -= 8;//4
            mY5 += 3;
        } else {
            mX5 = centerX;
            mY5 = centerY;
        }
        canvas.drawBitmap(mStarBitmap, mX5, mY5, mPaint);

//        Log.i("walke", "onDraw: ----------------------------------------");


        invalidate();

    }

}
