package walke.viewlibrary.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import walke.base.tool.WindowUtil;

/**
 * Created by Walke.Z
 * on 2017/9/25. 26.
 * email：1126648815@qq.com
 */
public class MarqueeText extends TextView implements Runnable {

    private int currentScrollX;// 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;
    private String mStr;
    private int mWidthMeasureSpec;

    public MarqueeText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthMeasureSpec = widthMeasureSpec;
        int textWidth = getTextWidth();
        Log.i("MarqueeText", "onMeasure: ------------->>>> textWidth = "+textWidth);
        super.onMeasure(textWidth, heightMeasureSpec);
    }

    /**
     * 获取文字宽度
     */
    private int getTextWidth() {
        Paint paint = this.getPaint();
        mStr = this.getText().toString();
        this.setText(mStr);
        textWidth = (int) paint.measureText(mStr);
        return textWidth;
    }
    @Override
    public void run() {
        currentScrollX += 2;// 滚动速度  //-
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
        /*if (getScrollX() <= -(this.getWidth())) {
            scrollTo(textWidth, 0);
            currentScrollX = textWidth;
            // return;
        }*/
        Log.i("MarqueeText", "run: mStr=："+mStr+"------this.getWidth() = "+this.getWidth()+"------getScrollX() = "+getScrollX()+"-----------textWidth = "+textWidth+"-----currentScrollX = "+currentScrollX);
        if (getScrollX() >= (textWidth)) {//
            //scrollTo(-textWidth, 0);//
            //currentScrollX = -textWidth;
            scrollTo(-WindowUtil.getWindowWidth(getContext()), 0);//
            currentScrollX = -WindowUtil.getWindowWidth(getContext());

            // return;
        }

        postDelayed(this, 5);
    }
    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }
    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }
    // 从头开始滚动
    public void startFor0() {
        currentScrollX = 0;
        startScroll();
    }
}
