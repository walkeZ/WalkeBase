package com.hui.huiheight.first.phone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hui.huiheight.ButterKnifeActivity;
import com.hui.huiheight.R;

/** Andorid截屏代码
 * Created by walke.Z on 2017/9/22.
 * http://www.cnblogs.com/llm-android/archive/2012/02/20/2358980.html
 */

public class ScreenShotActivity extends ButterKnifeActivity implements View.OnClickListener {

    ImageView mScreenshotImg1;

    ImageView mScreenshotImg2;

    Button mScreenshotButton;

    @Override
    public int layoutId() {
        return R.layout.activity_phone_screen_shot;
    }

    @Override
    protected void initData() {
        mScreenshotImg1 = ((ImageView) findViewById(R.id.screenshot_img1));
        mScreenshotImg2 = ((ImageView) findViewById(R.id.screenshot_img2));
        mScreenshotButton= (Button) findViewById(R.id.screenshot_button);
        mScreenshotButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        toast("screenshot_button");
        Bitmap shot = shot(this);
        mScreenshotImg2.setImageBitmap(shot);
    }

    /**  可以去除信息栏
     * @param activity
     * @return
     */
    private Bitmap shot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


}
