package com.hui.huiheight.util.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hui.huiheight.R;


/**
 * <P>协议展示对话框</P>
 * @author Create by skye
 * @date 2019-05-16
 */
public class AgreementDialog extends Dialog {
    private AgreeHelper mAgreeHelper;

    public AgreementDialog(Context context,String title,String content) {
       this(context, R.style.common_dialog,title,content);
    }

    public AgreementDialog(Context context, int themeResId,String title,String content) {
        super(context, themeResId);
        initDialog(context,title,content);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initDialog(Context context, String title, String content) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_agreement);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);    //弹出窗位置
        getWindow().setLayout(dp2px(context,315), ViewGroup.LayoutParams.WRAP_CONTENT);    //布局大小
        getWindow().setWindowAnimations(R.style.Animation_Dialog_buttom_in_out);    //动画效果，没动画就注释掉
        TextView tv_title = findViewById(R.id.dialog_agreement_title_tv);
        TextView tv_content = findViewById(R.id.dialog_agreement_content_tv);
        final Button btn_confirm =  findViewById(R.id.dialog_agreement_btn_confirm);
        tv_title.setText(title);
        tv_content.setText(content);
        findViewById(R.id.dialog_agreement_img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAgreeHelper!=null) mAgreeHelper.clickConfirm();
                cancel();
            }
        });

        CheckBox cb = findViewById(R.id.dialog_agreement_cb);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    btn_confirm.setEnabled(true);
                }else {
                    btn_confirm.setEnabled(false);
                }
            }
        });

    }

    public void setmAgreeHelper(AgreeHelper mAgreeHelper) {
        this.mAgreeHelper = mAgreeHelper;
    }

    public interface AgreeHelper{
        void clickConfirm();//点击了确定按钮
    }

    /**
     * dp转换成px
     */
    public static int dp2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
