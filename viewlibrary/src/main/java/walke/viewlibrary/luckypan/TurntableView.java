package walke.viewlibrary.luckypan;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import walke.viewlibrary.R;

import static android.content.ContentValues.TAG;

/**
 * Created by walke.Z on 2017/10/23.
 */

public class TurntableView extends FrameLayout implements View.OnClickListener {

    private ImageView ivTurntable;
    private ImageView ivPointer;
    private int mIndex;//item下标
    private int[] mOdds = new int[]{5,165,410,590,840,1000};//默认概率数组0~1000---0.5% , 16% , 24.5% , 18% , 25% , 16%

    public TurntableView(@NonNull Context context) {
        this(context,null);
    }

    public TurntableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TurntableView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_turntable,this);
        ivTurntable = ((ImageView) findViewById(R.id.turntable_bg));
        ivPointer = ((ImageView) findViewById(R.id.turntable_pointer));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TurntableView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TurntableView_imgTurntable) {
                int resourceId = a.getResourceId(attr, R.mipmap.turntable_bg1);
                ivTurntable.setImageResource(resourceId);

            } else if (attr == R.styleable.TurntableView_imgPointer) {
                int pointerId = a.getResourceId(attr, R.mipmap.turntable_pointer1);
                ivPointer.setImageResource(pointerId);

            }
            /*switch (attr) {
                case R.styleable.TurntableView_imgTurntable:
                    int resourceId = a.getResourceId(attr, R.mipmap.turntable_bg1);
                    ivTurntable.setImageResource(resourceId);
                    break;
                case R.styleable.TurntableView_imgPointer:
                    int pointerId = a.getResourceId(attr, R.mipmap.turntable_pointer1);
                    ivPointer.setImageResource(pointerId);
                    break;
            }*/
        }
        a.recycle();

        ivPointer.setOnClickListener(this);

    }

    public ImageView getIvTurntable() {
        return ivTurntable;
    }

    public ImageView getIvPointer() {
        return ivPointer;
    }

    public int getIndex() {
        return mIndex;
    }

    /**
     * @param odds 概率数组[1,1000]
     */
    public void setOdds(int[] odds) {
        mOdds = odds;
    }

    @Override
    public void onClick(View v) {
        ivPointer.setEnabled(false);
        runWithOdds();
    }
    /**   Math.random()  产生一个[0，1)之间的随机数
     *      概率数组[1,1000]
     */
    public void runWithOdds() {
        int randomNumber = (int) (1 + Math.random() * (1000));
        Log.i(TAG, "runWithOdds: -----------------> random = "+randomNumber);
        if (randomNumber<=mOdds[0]){
            mIndex =0;
            //            int randomAngle = (int) (331 + Math.random() * (59));
            int randomAngle = (int) (-27 + Math.random() * (57));//4--9
            rotateAttribute(randomAngle);
        }else if (mOdds[0]<randomNumber&&randomNumber<=mOdds[1]){
            mIndex =1;
            rotateAttribute((int) (33 + Math.random() * (57)));
        }else if (mOdds[1]<randomNumber&&randomNumber<=mOdds[2]){
            mIndex =2;
            rotateAttribute((int) (93 + Math.random() * (57)));
        }else if (mOdds[2]<randomNumber&&randomNumber<=mOdds[3]){
            mIndex =3;
            rotateAttribute((int) (153 + Math.random() * (57)));
        }else if (mOdds[3]<randomNumber&&randomNumber<=mOdds[4]){
            mIndex =4;
            rotateAttribute((int) (213 + Math.random() * (57)));
        }else {//
            mIndex =5;
            rotateAttribute((int) (273 + Math.random() * (57)));
        }

    }

    /**   Math.random()  产生一个[0，1)之间的随机数
     * @param odds 概率数组[1,1000]
     */
    public void runWithOdds(int[] odds) {
        int randomNumber = (int) (1 + Math.random() * (1000));
        Log.i(TAG, "runWithOdds: -------------------> randomNumber = "+randomNumber);

        if (randomNumber<=odds[0]){
            mIndex =0;
//            int randomAngle = (int) (331 + Math.random() * (59));
            int randomAngle = (int) (-31 + Math.random() * (59));
            rotateAttribute(randomAngle);
        }else if (odds[0]<randomNumber&&randomNumber<=odds[1]){
            mIndex =1;
            rotateAttribute((int) (31 + Math.random() * (59)));
        }else if (odds[1]<randomNumber&&randomNumber<=odds[2]){
            mIndex =2;
            rotateAttribute((int) (91 + Math.random() * (59)));
        }else if (odds[2]<randomNumber&&randomNumber<=odds[3]){
            mIndex =3;
            rotateAttribute((int) (151 + Math.random() * (59)));
        }else if (odds[3]<randomNumber&&randomNumber<=odds[4]){
            mIndex =4;
            rotateAttribute((int) (211 + Math.random() * (59)));
        }else {//
            mIndex =5;
            rotateAttribute((int) (271 + Math.random() * (59)));
        }

    }


    private void rotateAttribute(int randomAngle) {
        Log.i(TAG, "rotateAttribute: ----------> randomAngle = "+randomAngle);
        ObjectAnimator oa = ObjectAnimator.ofFloat(ivTurntable, "rotation" ,0, 360*12,360*14,360*15+randomAngle);
        oa.setRepeatCount(0);
        oa.setRepeatMode(ObjectAnimator.RESTART);
        //线型插值器，动画匀速执行
        oa.setInterpolator(new AccelerateDecelerateInterpolator());
        //int randomTime = (int) (3500 + Math.random() * (4500));
        oa.setDuration(3500);//4000
        oa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivPointer.setEnabled(false);
                ivPointer.setImageResource(R.mipmap.turntable_pointer2);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivPointer.setEnabled(true);
                ivPointer.setImageResource(R.mipmap.turntable_pointer1);
                if (mRotatingListener!=null)
                    mRotatingListener.rotateStop(mIndex);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        oa.start();
    }

    public interface RotatingListener{
        void rotateStop(int index);
    }

    private RotatingListener mRotatingListener;

    public void setRotatingListener(RotatingListener rotatingListener) {
        mRotatingListener = rotatingListener;
    }
}
