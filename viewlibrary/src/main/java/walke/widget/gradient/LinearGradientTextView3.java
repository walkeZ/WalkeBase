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

/**
 * author Walke - 2023/8/1 8:23 下午
 * email 1126648815@qq.ocm
 * dec : Android LinearGradient线性渐变 http://michael007js.cn/news/shownews.php?id=275
 */
public class LinearGradientTextView3 extends View {
    private Paint mPaint;
    private String str = "Android绘图小糊涂";
    private LinearGradient linearGradient;
    private float tran;

    public LinearGradientTextView3(Context context) {
        super(context);
    }

    public LinearGradientTextView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        int [] colors = {Color.BLACK,Color.RED, Color.BLUE,Color.BLACK};
        Rect rect = new Rect();
        mPaint.getTextBounds(str,0,str.length(), rect);
        int fontWidth = rect.width();
        linearGradient = new LinearGradient(0,0,-fontWidth+10,0,colors,null, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate(tran,0);
        linearGradient.setLocalMatrix(matrix);
        tran = (tran + 9) ;
        if (tran >= fontWidth*2){
            tran = 0;
        }
        mPaint.setShader(linearGradient);
        canvas.drawText(str,0,getMeasuredHeight()/2,mPaint);
        invalidate();
    }
}