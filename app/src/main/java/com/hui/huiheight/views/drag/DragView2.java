package com.hui.huiheight.views.drag;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


/**
 * @author walker
 * @description View 随手指滑动 -> 移动
 * @date 2022/5/7
 * https://blog.csdn.net/weixin_30200131/article/details/117485718
 */
public class DragView2 extends View {
    public float moveX = 70;
    public float moveY = 70;
    //定义。创建画笔
    Paint p = new Paint();
    private int mParentWidth;
    private int mParentHeight;

    public DragView2(Context context) {
        this(context, null);
    }

    public DragView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {
        //获取SingleTouchView所在父布局的中心点
        ViewGroup mViewGroup = (ViewGroup) getParent();
        if(null != mViewGroup){
            mParentWidth = mViewGroup.getWidth();
            mParentHeight = mViewGroup.getHeight();
            Log.i("DragView2", "onTouchEvent: ------>mParentHeight = " + mParentHeight);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 位于父控件底部要加原始偏移量：getTop()
                float yMove = event.getY() - moveY;
                float translationY = getY() + yMove - getTop();
                Log.i("DragView2", "onTouchEvent: ------>translationY = " + translationY
                        + "， yMove = " + yMove +",  event.getY() = " +  event.getY());
//                setTranslationY(translationY);
//                setScrollY((int) yMove);
//                scrollTo(200, (int) event.getY());
                scrollBy(200, (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }
        return true;

    }

    private void scrollTo(float translationY) {
    }
}
