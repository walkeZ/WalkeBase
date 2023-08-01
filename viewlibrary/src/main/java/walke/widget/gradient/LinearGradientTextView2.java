package walke.widget.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinearGradientTextView2 extends View {
    private Paint mPaint;

    public LinearGradientTextView2(Context context) {
        super(context);
    }

    public LinearGradientTextView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(70);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        float[] position = {0f, 0.6f, 1.0f};
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
// canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        canvas.drawText("Android绘图小糊涂", 0, getMeasuredHeight() / 2, mPaint);
    }
}