package com.hui.huiheight.first.chooselocation;

import android.content.Intent;
import android.view.View;

import com.hui.huiheight.R;
import com.hui.wheelviewlibrary.WheelViewActivity;

import java.util.ArrayList;
import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/2.
 * 选择省份市县三级联动
 */

public class ChoiceLocationActivity extends TitleActivity {
    private PickerView pv1,pv2,pv3;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_choice_location;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("选择省份市县三级联动");
        pv1 = ((PickerView) findViewById(R.id.acl_pv1));
        pv2 = ((PickerView) findViewById(R.id.acl_pv2));
        pv3 = ((PickerView) findViewById(R.id.acl_pv3));
    }

    @Override
    protected void initData() {
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            data.add("0" + i);
        }
        for (int i = 0; i < 60; i++)
        {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        pv1.setData(data);
        pv1.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                toast("选择了 " + text + " 分");
            }
        });
        pv2.setData(seconds);
        pv2.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                toast("选择了 " + text + " 秒");

            }
        });

        List<String> provinces = new ArrayList<String>();
        provinces.add("广东省");
        provinces.add("湖北省");
        provinces.add("江苏省");
        provinces.add("甘肃省");
        provinces.add("湖南省");
        pv3.setData(provinces);
        pv3.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                toast("选择了 " + text);
            }
        });

    }

    public void jump(View v){
        startActivity(new Intent(this, WheelViewActivity.class));
    }
}
