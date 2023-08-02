package walke.widget.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Created by walke.Z on 2018/4/17.
 */

public class ReflectTextView extends TimeView {

    private Matrix mMatrix;
    private Paint mPaint;
    private Bitmap mDrawingCache;

    public ReflectTextView(Context context) {
        this(context,null);
    }

    public ReflectTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReflectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mMatrix.preScale(1, -1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int)(getMeasuredHeight()*1.67));
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int height = getHeight();
//        int width = getWidth();
//        setDrawingCacheEnabled(true);
//        Bitmap originalImage = Bitmap.createBitmap(getDrawingCache());//StackOverflowError: stack size 8MB,堆内存溢出
//        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/5, width, height/2, mMatrix, false);
//        canvas.drawBitmap(reflectionImage, 0, height/3f, null);
//        if(mPaint == null)  {
//            mPaint = new Paint();
//            LinearGradient shader = new LinearGradient(0, height/2, 0,
//                    height, 0x7fffffff, 0x0fffffff, Shader.TileMode.CLAMP);
//            mPaint.setShader(shader);
//            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        }
//        canvas.drawRect(0, height/2f, width, height, mPaint);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        setDrawingCacheEnabled(true);

        new Thread(() -> mDrawingCache = getDrawingCache()).start();
        if (mDrawingCache!=null) {
            //E/AndroidRuntime: FATAL EXCEPTION: main
            //    Process: com.hui.huiheight, PID: 17825
            //    java.lang.IllegalArgumentException: cannot use a recycled source in createBitmap
            //        at android.graphics.Bitmap.createBitmap(Bitmap.java:1004)
            //        at walke.widget.text.ReflectTextView.onDraw(ReflectTextView.java:83)
            //        at android.view.View.draw(View.java:22004)
            //        at android.view.View.buildDrawingCacheImpl(View.java:21267)
            //        at android.view.View.buildDrawingCache(View.java:21121)
            //        at android.view.View.buildDrawingCache(View.java:21073)
            //        at walke.widget.text.ReflectTextView.onTextChanged(ReflectTextView.java:100)
            Bitmap reflectionImage = Bitmap.createBitmap(mDrawingCache, 0, height / 5, width, height / 2, mMatrix, false);
            canvas.drawBitmap(reflectionImage, 0, height / 3f, null);
            if (mPaint == null) {
                mPaint = new Paint();
                LinearGradient shader = new LinearGradient(0, height / 2, 0,
                        height, 0x7fffffff, 0x0fffffff, Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            }
            canvas.drawRect(0, height/2f, width, height, mPaint);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        buildDrawingCache();
        postInvalidate();
    }

}
