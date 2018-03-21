package walke.viewlibrary.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import walke.viewlibrary.R;

/**
 * Created by walke.Z on 2018/2/28.
 */

public class StarView3 extends View {
    private Bitmap mStarBitmap;
    private Paint mPaint = new Paint();
    private int mX1;
    private int mY1;
    private int mWidth;
    private int mHeight;
    private int centerX;
    private int centerY;
    private Bitmap[] mBitmaps;

    //所有数组个数需要一一对应

//    private float[] mScaleFactors = new float[]{0.3f,0.5f,0.7f,1f,2f};//缩放大小数组
    private float[] mScaleFactors = new float[]{0.8f,1.5f,2f,2.5f,3f,1.3f,1.9f,2.2f,3.5f};//缩放大小数组
    private int[] mMoveX = new int[]{-2,4,4,2,-5,4,2,-1,3};//与mMoveY一一对应形成移动角度
    private int[] mMoveY = new int[]{-3,-4,1,5,2,4,4,5,-3};//
    //    private int[] mMoveX = new int[]{-4,8,8,3,-8};//与mMoveY一一对应形成移动角度
//    private int[] mMoveY = new int[]{-8,-8,1,8,3};//

    //    private Point[] mPoint = new Point[]{new Point(),new Point(),new Point(),new Point(),new Point()};//
    private Point[] mPoint = new Point[9];//
    private Random mRandom = new Random();

    public StarView3(Context context) {
        this(context, null);
    }

    public StarView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStarBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.star);
        mBitmaps = new Bitmap[]{resizeBitmap(mScaleFactors[0]),resizeBitmap(mScaleFactors[1]),
                resizeBitmap(mScaleFactors[2]),resizeBitmap(mScaleFactors[3]),resizeBitmap(mScaleFactors[4]),
                resizeBitmap(mScaleFactors[5]),resizeBitmap(mScaleFactors[6]),resizeBitmap(mScaleFactors[7]),
                mStarBitmap};
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
        centerX = mWidth / 2 - mStarBitmap.getWidth() / 2;//不用对应每一个bitmap，应为在同一视图都同一个中心点
        centerY = mHeight / 2 - mStarBitmap.getHeight() / 2;
//        mX1 = mX2 = mX3 = mX4 = mX5 = centerX;
//        mY1 = mY2 = mY3 = mY4 = mY5 = centerY;
        mPoint[0]=new Point(centerX,centerY);
        mPoint[1]=new Point(centerX,centerY);
        mPoint[2]=new Point(centerX,centerY);
        mPoint[3]=new Point(centerX,centerY);
        mPoint[4]=new Point(centerX,centerY);
        mPoint[5]=new Point(centerX,centerY);
        mPoint[6]=new Point(centerX,centerY);
        mPoint[7]=new Point(centerX,centerY);
        mPoint[8]=new Point(centerX,centerY);
        Log.i("walke", "StarView2--onSizeChanged: -----------w=" + w + "    h=" + h + "    oldw=" + oldw + "    oldh=" + oldh);
        Log.i("walke", "StarView2--onSizeChanged: -----------getWidth=" + getWidth() + "    getHeight=" + getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //有重复的会有闪烁效果
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);
        drawItemBitmap(canvas);

//        drawItemBitmap(canvas,0);
//        drawItemBitmap(canvas,1);
//        drawItemBitmap(canvas,2);
//        drawItemBitmap(canvas,3);
//        drawItemBitmap(canvas,4);

//        if (mX2 < mWidth || mY2 > 0) {
//            mX2 += 8;//4
//            mY2 -= 8;
//        } else {
//            mX2 = centerX;
//            mY2 = centerY;
//        }
//        canvas.drawBitmap(mStarBitmap, mX2, mY2, mPaint);
//
//        if (mX3 < mWidth || mY3 < mHeight) {
//            mX3 += 8;//4
//            mY3 += 1;
//        } else {
//            mX3 = centerX;
//            mY3 = centerY;
//        }
//        canvas.drawBitmap(mStarBitmap, mX3, mY3, mPaint);
//
//        if (mX4 < mWidth || mY4 < mHeight) {
//            mX4 += 3;//4
//            mY4 += 8;
//        } else {
//            mX4 = centerX;
//            mY4 = centerY;
//        }
//        canvas.drawBitmap(mStarBitmap, mX4, mY4, mPaint);
//
//        if (mX5 > 0 || mY5 < mHeight) {
//            mX5 -= 8;//4
//            mY5 += 3;
//        } else {
//            mX5 = centerX;
//            mY5 = centerY;
//        }
//        canvas.drawBitmap(mStarBitmap, mX5, mY5, mPaint);

//        Log.i("walke", "onDraw: ----------------------------------------");


        invalidate();

    }

    private void drawItemBitmap(Canvas canvas) {
        int index = mRandom.nextInt(9);//随机产生0~8中的一个数
        mPaint.setAlpha(255);//0~255
        boolean fitX = mPoint[index].x > 0 && mPoint[index].x < mWidth;
        boolean fitY = mPoint[index].y > 0 && mPoint[index].y < mHeight;
        if (fitX || fitY) {
            mPoint[index].x = mPoint[index].x+mMoveX[index];
            Log.i("walke", "drawItemBitmap: ------index="+index+"---mMoveX[index]="+mMoveX[index]+"---mPoint[index].x"+mPoint[index].x);
            mPoint[index].y = mPoint[index].y+mMoveY[index];
            Log.i("walke", "drawItemBitmap: ------index="+index+"---mMoveY[index]="+mMoveY[index]+"---mPoint[index].y"+mPoint[index].y);
        } else {
            mPoint[index].x = centerX;
            mPoint[index].y = centerY;
        }
        canvas.drawBitmap(mBitmaps[index], mPoint[index].x, mPoint[index].y, mPaint);
//        mPaint.setAlpha(255);//0~255
//        boolean fitX = mX1 > 0 && mX1 < mWidth;
//        boolean fitY = mY1 > 0 && mY1 < mHeight;
//        if (fitX || fitY) {
//            mX1 = mX1+mMoveX[index];
//            mY1 = mY1+mMoveY[index];
//        } else {
//            mX1 = centerX;
//            mY1 = centerY;
//        }
//        canvas.drawBitmap(mStarBitmap, mX1, mY1, mPaint);
    }
    private void drawItemBitmap(Canvas canvas,int index) {
        mPaint.setAlpha(255);//0~255
        boolean fitX = mPoint[index].x > 0 && mPoint[index].x < mWidth;
        boolean fitY = mPoint[index].y > 0 && mPoint[index].y < mHeight;
        if (fitX || fitY) {
            mPoint[index].x = mPoint[index].x+mMoveX[index];
            Log.i("walke", "drawItemBitmap: ------index="+index+"---mMoveX[index]="+mMoveX[index]+"---mPoint[index].x"+mPoint[index].x);
            mPoint[index].y = mPoint[index].y+mMoveY[index];
            Log.i("walke", "drawItemBitmap: ------index="+index+"---mMoveY[index]="+mMoveY[index]+"---mPoint[index].y"+mPoint[index].y);
        } else {
            mPoint[index].x = centerX;
            mPoint[index].y = centerY;
        }
        canvas.drawBitmap(mBitmaps[index], mPoint[index].x, mPoint[index].y, mPaint);
    }

    private Bitmap resizeBitmap(float scale){
        Matrix m = new Matrix();
        m.setScale(scale, scale);
        Bitmap resizeBitmap = Bitmap.createBitmap(mStarBitmap,0,0,mStarBitmap.getWidth(),mStarBitmap.getHeight(),m,true);
        return resizeBitmap;
    }

}
