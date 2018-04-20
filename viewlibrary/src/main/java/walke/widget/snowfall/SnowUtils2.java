package walke.widget.snowfall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import walke.widget.R;

/**
 * change by walke on 17-5-27.
 */

public class SnowUtils2 {

    private Context mContext;

    public static final int SNOW_LEVEL_SMALL = 1;
    public static final int SNOW_LEVEL_MIDDLE = 2;
    public static final int SNOW_LEVEL_HEAVY = 3;

    private static final int SNOW_NUM_PER_TIME_SMALL = 3;
    private static final int SNOW_NUM_PER_TIME_MIDDLE = 4;
    private static final int SNOW_NUM_PER_TIME_HEAVY = 5;

    private static final int MAX_SPEED_SMALL = 20;
    private static final int MAX_SPEED_MIDDLE = 30;
    private static final int MAX_SPEED_HEAVY = 40;

    private static final int MIN_SPEED_SMALL = 6;
    private static final int MIN_SPEED_MIDDLE = 7;
    private static final int MIN_SPEED_HEAVY = 10;

    private static final int PRODUCE_SNOW_INTERVAL_SMALL = 500;
    private static final int PRODUCE_SNOW_INTERVAL_MIDDLE = 350;
    private static final int PRODUCE_SNOW_INTERVAL_HEAVY = 200;

    private int SNOW_FLAKE_MAX_COUNT = 300;
    private float SCALE_MIN = 0.5f;

//    private float[] mAlphas = new float[]{0.3f,0.5f,0.6f,0.8f,1f};
//    private float[] mSpeedFactors = new float[]{0.5f,0.7f,0.8f,0.9f,1f};
//    private float[] mScaleFactors = new float[]{0.3f,0.4f,0.6f,0.8f,1f};
    private float[] mAlphas = new float[]{0.3f,0.5f,0.6f,0.8f,1f};//透明度数组
    private float[] mSpeedFactors = new float[]{0.5f,0.7f,0.8f,0.9f,1f};//飘落速度数组
    private float[] mScaleFactors = new float[]{0.1f,0.3f,0.4f,0.5f,0.6f};//缩放大小数组

    private Random mRandom = new Random();
    private Paint mPaint = new Paint();
    private int mSnowLevel = SNOW_LEVEL_MIDDLE;
    private int mProduceNumPerTime = SNOW_NUM_PER_TIME_MIDDLE;
    private int mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_MIDDLE;

    private Bitmap mSnowBitmap;
    private ArrayList<SnowFlake2> mSnowFlake2List;
    private Bitmap[] mBitmaps;
    private int mMaxSpeed,mMinSpeed;
    private int mHeight;
    private int mWidth;
    private float degrees=10;


    public SnowUtils2(Context context){
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(int width, int height){
        mHeight = height;
        mWidth = width;
        initSnowFlakes();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//ndk:21
    private void initSnowFlakes(){
        mSnowBitmap = ((BitmapDrawable)(mContext.getResources().getDrawable(R.drawable.snow, mContext.getTheme()))).getBitmap();
        mBitmaps = new Bitmap[]{resizeBitmap(mScaleFactors[0]),resizeBitmap(mScaleFactors[1]),
                resizeBitmap(mScaleFactors[2]),resizeBitmap(mScaleFactors[3]),mSnowBitmap};
        mSnowFlake2List = new ArrayList<>(SNOW_FLAKE_MAX_COUNT);
        for(int i = 0; i < SNOW_FLAKE_MAX_COUNT; i++){
            SnowFlake2 snow = new SnowFlake2();
            mSnowFlake2List.add(snow);
        }
    }

    private Bitmap resizeBitmap(float scale){
        Matrix m = new Matrix();
        m.setScale(scale, scale);
        Bitmap resizeBitmap = Bitmap.createBitmap(mSnowBitmap,0,0,mSnowBitmap.getWidth(),mSnowBitmap.getHeight(),m,true);
        return resizeBitmap;
    }

    private void updateSnowFlake(){
        for(int i = 0; i < mSnowFlake2List.size(); i++){
            SnowFlake2 snow = mSnowFlake2List.get(i);
            if(snow.isLive){
                long currentTime = SystemClock.uptimeMillis();
                int offsetY = (int)(((float)(currentTime - snow.startTimeVertical))/100 * snow.speedVertical);
                snow.y = snow.startY + offsetY;

                if(snow.y > mHeight){
                    snow.isLive = false;
                }
            }
        }
    }
    Camera mCamera = new Camera();
    public void draw(Canvas canvas){
        for(int i = 0; i < mSnowFlake2List.size(); i++) {
            SnowFlake2 snow = mSnowFlake2List.get(i);
            if(snow.isLive){
                int save = canvas.save();

                //新增-------
//                Matrix mMatrix=new Matrix();
//                mMatrix.reset();
//                mMatrix.setScale(snow.scale, snow.scale);
//                mMatrix.setScale(1.0f, 1.0f);
//                if (degrees<40){
//                    degrees++;
//                }else {
//                    degrees=10;
//                }
//                mMatrix.setRotate(degrees);
//                canvas.setMatrix(mMatrix);


                //新增-------
                Matrix mMatrix=new Matrix();
                mCamera.save();
                if (degrees<360){
                    degrees++;
                }else {
                    degrees=0;
                }
                mCamera.rotateY(degrees);
                mCamera.getMatrix(mMatrix);
                int centerX = mBitmaps[snow.index].getWidth() / 2;
                int centerY = mBitmaps[snow.index].getHeight() / 2;
                mMatrix.preTranslate(-centerX, -centerY);
                mMatrix.postTranslate(centerX, centerY);
                mCamera.restore();
                canvas.setMatrix(mMatrix);
                Log.i("walke", "SnowUtils3 draw: -------> centerX = "+centerX+" ---> centerY = "+centerY );



                mPaint.setAlpha(snow.alpha);
                canvas.drawBitmap(mBitmaps[snow.index], snow.x, snow.y, mPaint);
                canvas.restoreToCount(save);
            }
        }
        updateSnowFlake();
    }


    public void setSnowLevel(int level){
        mSnowLevel = level;

        switch (mSnowLevel){
            case SNOW_LEVEL_SMALL:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_SMALL;
                mMaxSpeed = MAX_SPEED_SMALL;
                mMinSpeed = MIN_SPEED_SMALL;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_SMALL;
                break;
            case SNOW_LEVEL_MIDDLE:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_MIDDLE;
                mMaxSpeed = MAX_SPEED_MIDDLE;
                mMinSpeed = MIN_SPEED_MIDDLE;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_MIDDLE;
                break;
            case SNOW_LEVEL_HEAVY:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_HEAVY;
                mMaxSpeed = MAX_SPEED_HEAVY;
                mMinSpeed = MIN_SPEED_HEAVY;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_HEAVY;
                break;
        }
    }

    public int getProduceSnowInterval(){
        return mProduceSnowInterval;
    }


    public void removeAllSnowFlake(){
        for(int i = 0; i < mSnowFlake2List.size(); i++){
            SnowFlake2 snow = mSnowFlake2List.get(i);
            if(snow.isLive){
                snow.isLive = false;
            }
        }
    }

    public void produceSnowFlake(){
        int produceCount = 0;
        for(int i = 0; i < mSnowFlake2List.size(); i++){
            SnowFlake2 snow = mSnowFlake2List.get(i);
            if(!snow.isLive){
                int index = mRandom.nextInt(4);
                snow.isLive = true;
                int edge = mContext.getResources().getDimensionPixelOffset(R.dimen.snow_edge);
                snow.x = mRandom.nextInt(mWidth - edge * 2) + edge;
                snow.y = -edge;
                snow.startX = snow.x;
                snow.startY = snow.y;
                snow.alpha = (int)(mAlphas[index]*255);//mRandom.nextInt(155)+100;
                snow.speedVertical = (int)((mRandom.nextInt(mMaxSpeed - mMinSpeed)+mMinSpeed) * mSpeedFactors[index]);//mRandom.nextInt(20)+5;
//                float scale = mRandom.nextFloat();
//                scale = scale > SCALE_MIN ? scale : SCALE_MIN;
//                snow.scale = scale;

                snow.index = index;

                long currentTime = SystemClock.uptimeMillis();
                snow.startTimeHorizontal = snow.startTimeVertical = currentTime;

                produceCount++;
                if(produceCount >= mProduceNumPerTime){
                    break;
                }
            }
        }
    }
}
