package walke.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import walke.base.tool.LogUtil;
import walke.base.tool.ToastUtil;


/**
 * Created by Walke.Z on 2017/4/21.
 * 这是底层(第一层)封装
 *
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        //StatusBarCompat.compat(this, Color.RED);
         //hideVirtualButtons();
         initState();
    }

    /**调用该方法后，虚拟案件就会被隐藏 从屏幕底部向上拉，可以再次显示
     * 与布局xml文件 以下两个属性连用[而6.0异常]
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     * 沉浸式导航栏(虚拟按键栏)会导致上下边界异常
     */
    @SuppressLint("NewApi")
    private void hideVirtualButtons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 在需要实现沉浸式状态栏的Activity的布局中添加以下参数
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           // 只沉浸式状态栏，与布局xml文件 以下两个属性连用[而6.0也正常]
           // android:clipToPadding="true"
           // android:fitsSystemWindows="true"

           //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * @param editText 获取焦点
     */
    public void editTextGetFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        /*boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开 
        if (!isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }*/
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public String replaceEmptyText(String emptyText,String tagText){
        if (TextUtils.isEmpty(emptyText))
            return tagText;
        else
            return emptyText;
    }

    /**
     * @param editText 进入界面后，输入框获取焦点
     *                 需要延迟
     */
    public void enterGetFocus(final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editTextGetFocus(editText);
            }
        }, 200);
    }

    public void showRequestPermissionDialog() {
       /* DialogUtil.createTwoButtonDialog(this, "兑奖使用帮助","需允许摄像头进行扫一扫:", "取消", "设置", "", new DialogUtil.DialogTwoButtonClickListener() {
            @Override
            public void leftOnClick(WindowManager.LayoutParams lp, Dialog dialog) {

            }

            @Override
            public void rightOnClick(WindowManager.LayoutParams lp, Dialog dialog) {
                dialog.dismiss();
                openSystemSetting();
            }
        });*/

    }
    /**
     * 打开系统设置界面
     */
    public void openSystemSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public void toast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showToast(this,message);
    }
    public void middleToast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showMidlleToast(this,message);
    }
    public void toastTime(String message,int time){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showToastWithTime(this,message,time);
    }
    public void logI(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.i(this.getClass().getSimpleName(),"-------------------> "+message);
    }
    public void logD(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.d(this.getClass().getSimpleName(),"--------> "+message);
    }
    public void logE(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.e(this.getClass().getSimpleName(),"---------      -----------> "+message);
    }


}
