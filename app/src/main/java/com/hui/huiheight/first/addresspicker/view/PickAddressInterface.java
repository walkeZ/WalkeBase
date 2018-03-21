package com.hui.huiheight.first.addresspicker.view;

import com.hui.huiheight.first.addresspicker.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface PickAddressInterface {
    void onSureClick(String name, List<AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean> beans);
    void onCancelClick();
}
