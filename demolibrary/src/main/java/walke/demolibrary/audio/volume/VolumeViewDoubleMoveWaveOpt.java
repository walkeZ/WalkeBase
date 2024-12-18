package walke.demolibrary.audio.volume;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

public class VolumeViewDoubleMoveWaveOpt extends View {

    private Paint mPaint;

    private Path mPath;

    private float mDrawHeight;

    private float mDrawWidth;

    private float mViewHeight;

    private float mViewWidth;

    private float mCenterPointX;

    private float mCenterPointY;

    private double mPhase; // 相位

    private static final float MOVE_DISTACE = 5f;

    private MoveThread mMoveThread;

    // small wave 是外面的小波，big wave 是里面的大波

    private static final int SMALL_WAVE_COLOR = 0xFF77DAFD;

    private static final int BIG_WAVE_COLOR = 0xFF4DA6F3;

    private static final float SMALL_WAVE_SPEED = 1f;

    private static final float BIG_WAVE_SPEED = 1.5f;

    private static final int SMALL_WAVE_PERIOD = 5;

    private static final int BIG_WAVE_PERIOD = 6;

    private float mMaxBigWaveAmplitude;

    private float mMaxSmallWaveAmplitude;

    private static final float BIG_SINE_UP_AND_DOWN_MOVE = 1.8f;

    private static final float SMALL_SINE_UP_AND_DOWN_MOVE = 1.3f;

    private static final float SMALL_AMPLITUDE_SIZE_SCALE = 8f;

    private static final float BIG_AMPLITUDE_SIZE_SCALE = 6f;

    public VolumeViewDoubleMoveWaveOpt(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = measureWidth(widthMeasureSpec);
        heightMeasureSpec = measureHeight(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        if (mViewWidth == viewWidth && mViewHeight == viewHeight) {
            return;
        }
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        mDrawWidth = mViewWidth - paddingLeft - paddingRight;
        mDrawHeight = mViewHeight - paddingTop - paddingBottom;

        mCenterPointX = paddingLeft + mDrawWidth / 2f;
        mCenterPointY = paddingTop + mDrawHeight / 2f;

        mMaxSmallWaveAmplitude = mDrawHeight / SMALL_AMPLITUDE_SIZE_SCALE;
        mMaxBigWaveAmplitude = mDrawHeight / BIG_AMPLITUDE_SIZE_SCALE;
    }

    private int measureWidth(int spec) {
        int mode = MeasureSpec.getMode(spec);
        if (mode == MeasureSpec.UNSPECIFIED) {
            WindowManager wm = (WindowManager) getContext().getSystemService(
                    Context.WINDOW_SERVICE);

            @SuppressWarnings("deprecation")
            int width = wm.getDefaultDisplay().getWidth();
            spec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        } else if (mode == MeasureSpec.AT_MOST) {
            int value = MeasureSpec.getSize(spec);
            spec = MeasureSpec.makeMeasureSpec(value, MeasureSpec.EXACTLY);
        }
        return spec;
    }

    private int measureHeight(int spec) {
        int mode = MeasureSpec.getMode(spec);
        if (mode == MeasureSpec.EXACTLY) {
            return spec;
        }

        int height = (int) dip2px(50); // 其他模式下的最大高度

        if (mode == MeasureSpec.AT_MOST) {
            int preValue = MeasureSpec.getSize(spec);
            if (preValue < height) {
                height = preValue;
            }
        }
        spec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        return spec;
    }

    private float dip2px(float dp) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    private double sine(float x, int period, float drawWidth, double phase) {
        return Math.sin(2 * Math.PI * period * (x + phase) / drawWidth);
    }

    private void drawSine(Canvas canvas, Path path, Paint paint, int period,
            float drawWidth, float amplitude, double phase, float upAndDownScale) {
        float halfDrawWidth = drawWidth / 2f;
        path.reset();
        path.moveTo(-halfDrawWidth, 0);
        float y;
        double scaling;
        for (float x = -halfDrawWidth; x <= halfDrawWidth; x++) {
            scaling = 1 - Math.pow(x / halfDrawWidth, 2);// 对y进行缩放
            y = (float) ((sine(x, period, drawWidth, phase) + upAndDownScale)
                    * amplitude * (1) * Math.pow(scaling, 3));
            path.lineTo(x, y);
        }

        for (float x = halfDrawWidth; x >= -halfDrawWidth; x--) {
            scaling = 1 - Math.pow(x / halfDrawWidth, 2);// 对y进行缩放
            y = (float) ((sine(x, period, drawWidth, phase) + upAndDownScale)
                    * amplitude * (-1) * Math.pow(scaling, 3));
            path.lineTo(x, y);
        }

        canvas.drawPath(path, paint);
        canvas.save();
        canvas.restore();
    }

    private void drawWave(Canvas canvas, Path path, Paint paint, int period,
            float drawWidth, double phase) {
        paint.setColor(BIG_WAVE_COLOR);
        drawSine(canvas, mPath, mPaint, BIG_WAVE_PERIOD, mDrawWidth,
                mMaxBigWaveAmplitude, mPhase * BIG_WAVE_SPEED,
                BIG_SINE_UP_AND_DOWN_MOVE);
        paint.setColor(SMALL_WAVE_COLOR);
        drawSine(canvas, mPath, mPaint, SMALL_WAVE_PERIOD, mDrawWidth,
                mMaxSmallWaveAmplitude, mPhase * SMALL_WAVE_SPEED,
                SMALL_SINE_UP_AND_DOWN_MOVE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mCenterPointX, mCenterPointY);
        drawWave(canvas, mPath, mPaint, BIG_WAVE_PERIOD, mDrawWidth, mPhase);

    }

    private class MoveThread extends Thread {
        private static final int MOVE_STOP = 1;

        private static final int MOVE_START = 0;

        private int state;

        @Override
        public void run() {
            mPhase = 0;
            state = MOVE_START;
            while (true) {
                if (state == MOVE_STOP) {
                    break;
                }
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    // ignore
                }
                mPhase -= MOVE_DISTACE;
                postInvalidate();
            }
        }

        public void stopRunning() {
            state = MOVE_STOP;
        }
    }

    /*
     * API
     */

    // start volume animation
    public void start() {
        mPhase = 0;
        invalidate();
        if (mMoveThread != null) {
            mMoveThread.stopRunning();
            mMoveThread = null;
        }
        mMoveThread = new MoveThread();
        mMoveThread.start();
    }

    // stop volume animation
    public void stop() {
        if (mMoveThread != null) {
            mMoveThread.stopRunning();
            mMoveThread = null;
        }
        postInvalidate();
    }

}
