package com.hui.huiheight.first.addresspicker;

import com.hui.huiheight.first.addresspicker.bean.AddressInfo;
import com.hui.huiheight.first.addresspicker.bean.AreaInfo;

/**
 * Created by walke.Z on 2018/2/23.
 */

public class AddressUtil {
    public static AreaInfo getCurrentProvince(AddressInfo addressInfo){
        for (AreaInfo areaInfo : addressInfo.getProvinceList()) {
            Integer provinceId = addressInfo.getProvinceId();
            if (provinceId.equals(areaInfo.getAreaId()))
                return areaInfo;
        }
        return null;
    }
    public static AreaInfo getCurrentCity(AddressInfo addressInfo){
        for (AreaInfo areaInfo : addressInfo.getCityList()) {
            Integer cityId = addressInfo.getCityId();
            if (cityId.equals(areaInfo.getAreaId()))
                return areaInfo;
        }
        return null;
    }
    public static AreaInfo getCurrentZone(AddressInfo addressInfo){
        for (AreaInfo areaInfo : addressInfo.getZoneList()) {
            Integer cityId = addressInfo.getZoneId();
            if (cityId.equals(areaInfo.getAreaId()))
                return areaInfo;
        }
        return null;
    }
    public static AreaInfo getCurrentStreet(AddressInfo addressInfo){
        for (AreaInfo areaInfo : addressInfo.getStreetList()) {
            Integer streetId = addressInfo.getStreetId();
            if (streetId.equals(areaInfo.getAreaId()))
                return areaInfo;
        }
        return null;
    }
}
