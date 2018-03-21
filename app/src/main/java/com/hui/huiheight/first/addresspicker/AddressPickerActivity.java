package com.hui.huiheight.first.addresspicker;

import android.view.View;

import com.hui.huiheight.R;
import com.hui.huiheight.first.addresspicker.bean.AddressInfo;
import com.hui.huiheight.first.addresspicker.bean.AreaInfo;
import com.hui.huiheight.first.addresspicker.view.PickAddressInterface;
import com.hui.huiheight.first.addresspicker.view.PickAddressThreeView;
import com.hui.huiheight.first.addresspicker.view.PickAddressView;

import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2018/2/23.
 */

public class AddressPickerActivity extends TitleActivity {
    private PickAddressView fourPicker;
    private PickAddressThreeView threePick;

    List<AddressBean> addressBeanList;

    private MyPickAddressView myFourPicker;
    private AddressInfo mAddressInfo;


    @Override
    protected int rootLayoutId() {
        return R.layout.activity_address_picker;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("AddressPickerActivity");
        fourPicker = ((PickAddressView) findViewById(R.id.address_picker_fourPicker));
        threePick = ((PickAddressThreeView) findViewById(R.id.address_picker_threePicker));
        addressBeanList=DataHelper.getAddress(this);
        fourPicker.setData(addressBeanList);
        fourPicker.setOnTopClicklistener(new PickAddressInterface() {
            @Override
            public void onSureClick(String name, List<AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean> beans) {
                toast("name="+name+"\n"+beans.toString());
            }

            @Override
            public void onCancelClick() {
                fourPicker.setVisibility(View.GONE);
            }
        });


        addressBeanList = DataHelper.getAddress(this);
        threePick.setData(addressBeanList);
        threePick.setOnTopClicklistener(new PickAddressInterface() {

            @Override
            public void onSureClick(String name, List<AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean> beans) {
                toast("name="+name+"\n"+beans.toString());
            }

            @Override
            public void onCancelClick() {
                threePick.setVisibility(View.GONE);
            }
        });

        myFourPicker = ((MyPickAddressView) findViewById(R.id.address_picker_myFourPicker));
        mAddressInfo = DataHelper.getAddressInfo(this);
        myFourPicker.setData(mAddressInfo);
        myFourPicker.setOnTopClicklistener(new MyPickAddressInterface() {
            @Override
            public void onSureClick(AddressInfo aInfo) {
                AreaInfo areaInfo = AddressUtil.getCurrentProvince(aInfo);
                String areaName = areaInfo.getAreaName();
                toast("id="+aInfo.getProvinceId()+"\n name="+ areaName);
            }

            @Override
            public void onCancelClick() {
                fourPicker.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void initData() {

    }
    @Override
    protected void onDestroy() {
        fourPicker.onDistory();
        addressBeanList.clear();
        addressBeanList = null;
        fourPicker.removeAllViews();
        fourPicker = null;
        super.onDestroy();
    }
}
