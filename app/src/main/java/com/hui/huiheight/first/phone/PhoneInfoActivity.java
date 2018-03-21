package com.hui.huiheight.first.phone;

import android.widget.TextView;

import com.hui.huiheight.R;

import java.util.Map;

import walke.base.activity.TitleActivity;
import walke.base.tool.PhoneUtil;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/17.
 */

public class PhoneInfoActivity extends TitleActivity {
    private TextView tvPhoneInfo;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_phone_info;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("手机信息");
        tvPhoneInfo = ((TextView) findViewById(R.id.api_tvPhoneInfo));
        String phoneInfo = PhoneUtil.getPhoneInfo(this);
        String totalMemory = PhoneUtil.getTotalMemory(this);
        String availMemory = PhoneUtil.getAvailMemory(this);
        String[] cpuInfo = PhoneUtil.getCpuInfo();
        int numCores = PhoneUtil.getNumCores();
        Map<String, Integer> map = PhoneUtil.windowSize(this);
        tvPhoneInfo.setText(
                "phoneInfo: "+phoneInfo+"\ntotalMemory: "+totalMemory+
                "\navailMemory: "+availMemory+"\ncpuInfo: "+cpuInfo+"\nnumCores: "+numCores+
                 "\n map: "+map.toString());



    }

    @Override
    protected void initData() {


    }
}
