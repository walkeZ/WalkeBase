package com.hui.huiheight.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hui.huiheight.R;

import walke.base.activity.BaseActivity;

/**
 * Created by walke.Z on 2018/2/27.
 */

public class DialogUtil {


    /**
     * 显示更换头像提示框
     */
    public static void showChangeHeaderTips(final Context context, View view , final PopupWindowTwoButtonClickListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_change_header_tips, null);
        TextView photos = (TextView) inflate.findViewById(R.id.cht_photos);
        TextView camera = (TextView) inflate.findViewById(R.id.cht_camera);
        final PopupWindow pw = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pw.setAnimationStyle(R.style.pwAnimationBottom);
        // popup window点击窗口外区域消失的解决方法 --顺序前后问题
        pw.setTouchable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());//必须设置背景
        pw.setFocusable(true);// 设置让pw获得焦点
        //设置pw出现方式
        pw.showAtLocation(view, Gravity.BOTTOM, 0,0);// ViewUtil.dipTopx(context, 45)
       /* //popup window点击窗口外区域不消失的解决方法
        pw.setTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);*/
        // 设置背景颜色变暗
        final WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
        lp.alpha = 0.7f;
        ((BaseActivity) context).getWindow().setAttributes(lp);

        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((BaseActivity) context).getWindow().setAttributes(lp);
            }
        });

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                if (listener!=null)
                    listener.OnTopClick();

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
                if (listener!=null)
                    listener.OnBottomClick();
            }
        });
    }


    public interface DialogTwoButtonClickListener {
        void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog);

        void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog);
    }
    public interface PopupWindowTwoButtonClickListener {
        void OnTopClick();

        void OnBottomClick();
    }

}
