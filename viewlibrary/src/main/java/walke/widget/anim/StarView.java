package walke.widget.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import walke.widget.R;

/**
 * Created by walke.Z on 2018/2/28.
 */

public class StarView extends View {
    private Bitmap mStarBitmap;
    private Paint mPaint = new Paint();
    private int mX;
    private int mY;
    private int mWidth;
    private int mHeight;
    private int mSpeed = 3;
    private int mSpeedY = mSpeed + 4;
    private boolean isDown = true;

    public StarView(Context context) {
        this(context, null);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("walke", "onMeasure: ---------widthMeasureSpec=" + widthMeasureSpec + "    heightMeasureSpec=" + heightMeasureSpec);
        Log.i("walke", "onMeasure: ---------getWidth=" + getWidth() + "    getHeight=" + getHeight());
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
//        mWidth = 1000;
//        mHeight = 300;
        Log.i("walke", "onSizeChanged: -----------w=" + w + "    h=" + h + "    oldw=" + oldw + "    oldh=" + oldh);
        Log.i("walke", "onSizeChanged: -----------getWidth=" + getWidth() + "    getHeight=" + getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mStarBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.star);
        mPaint.setAlpha(255);//0~255
        mX += mSpeed;//4
        if (isDown)
            mY += mSpeedY;
        canvas.drawBitmap(mStarBitmap, mX, mY, mPaint);
        /*if (mX>mWidth||mY>mHeight){//重复
            mX=0;
            mY=0;
        }*/

        if (mWidth > mHeight) {//镜面弹射
            if (isDown) {
                if (mY >= mHeight) {
                    isDown = false;

                }
            }else {
                if (mY > 0) {
                    mY = mY - mSpeedY;//前面有加1个
//                    Log.i("walke", "onDraw: ---------------my=" + mY);
                } else {
                    isDown = true;
                }
            }
            if (mX > mWidth) {
                isDown = true;
                mX = 0;
                mY = 0;
            }
        }

        invalidate();

    }

}
