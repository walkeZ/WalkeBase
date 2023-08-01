package walke.widget.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import walke.base.tool.ViewUtil;

public class LinearGradientTextView extends View implements Runnable {

    private Paint mPaint;
    private Matrix mMatrix;
    private String mText;
    private LinearGradient mLinearGradient;
    private long mDuration = 50;
    private int mTransX = 0;
    private Rect rect = new Rect();
    private LinearGradient mKrcLinearGradient;
    private float[] mfKrc = new float[2];
    private String mSingText;
    private Paint mSingPaint;


    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();
        mText = "疏疏晴雨弄斜阳，凭栏久，墙外杏花香";
        mSingText = "很爱很爱你所以愿意，不牵绊你";
        mPaint.setTextSize(ViewUtil.dpToPx(getContext(), 16));
        mSingPaint.setTextSize(ViewUtil.dpToPx(getContext(), 16));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mLinearGradient) {
            mLinearGradient = new LinearGradient(0, 0, 200, 0,
                    new int[]{Color.parseColor("#440000"),
                            Color.parseColor("#ffffff"),
                            Color.parseColor("#440000")},
                    new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP);
            mLinearGradient.setLocalMatrix(mMatrix);
            mPaint.setShader(mLinearGradient);
            mfKrc[0] = 0.0f;
            mfKrc[1] = 0.0f;
            mKrcLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, new int[]{
                    Color.parseColor("#ff0000"),
                    Color.parseColor("#ffffff")},
                    mfKrc,
                    Shader.TileMode.CLAMP);
            mSingPaint.setShader(mKrcLinearGradient);
            postDelayed(this, mDuration);
        }
        mPaint.getTextBounds(mText, 0, mText.length(), rect);
        canvas.drawText(mText, 0, rect.bottom - rect.top, mPaint);
        canvas.drawText(mSingText, 0, getMeasuredHeight() / 2, mSingPaint);
    }

    @Override
    public void run() {
        mTransX += 10;
        if (mTransX > rect.right - rect.left) {
            mTransX = -10;
        }
        mfKrc[0] += 0.01f;
        mfKrc[1] = mfKrc[0];
        if (mfKrc[0] > 1.0f) {
            mfKrc[0] = 0.0f;
            mfKrc[1] = 0.0f;
        }
        mKrcLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, new int[]{
                Color.parseColor("#ff0000"),
                Color.parseColor("#ffffff")},
                mfKrc,
                Shader.TileMode.CLAMP);
        mSingPaint.setShader(mKrcLinearGradient);
        mMatrix.setTranslate(mTransX, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        invalidate();
        postDelayed(this, mDuration);

    }
}