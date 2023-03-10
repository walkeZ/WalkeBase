package com.hui.huiheight.first.addresspicker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hui.huiheight.R;
import com.hui.huiheight.first.addresspicker.bean.AddressInfo;
import com.hui.huiheight.first.addresspicker.bean.AreaInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuxuliangcumt on 2017/10/30.
 * 四级联动
 */

public class MyPickAddressView extends LinearLayout {

    private MyWheelView mProvincePicker;
    private MyWheelView mCityPicker;
    private MyWheelView mZonePicker;
    private MyWheelView mStreetPicker;

    protected AreaInfo mCurrentProvince, mCurrentCity,mCurrentZone, mCurrentStreet;
    private TextView tvCancel, tvSure;
    private MyPickAddressInterface mMyPickAddressInterface;


    public MyPickAddressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        LayoutInflater.from(context).inflate(R.layout.view_my_address_four, this);
        initData();
    }

    public MyPickAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initData() {
        mProvincePicker = (MyWheelView) findViewById(R.id.myProvince);//省份item
        mCityPicker = (MyWheelView) findViewById(R.id.myCity);
        mZonePicker = (MyWheelView) findViewById(R.id.myCounty);
        mStreetPicker = (MyWheelView) findViewById(R.id.myStreet);
        tvCancel = (TextView) findViewById(R.id.myBox_cancel);
        tvSure = (TextView) findViewById(R.id.myBox_ok);

        mProvincePicker.setOnSelectListener(new MyWheelView.OnSelectListener() {
            @Override
            public void endSelect(AreaInfo areaInfo) {//滑动后选了xx省份
                // TODO: 2018/2/23 ①设置省份、保存当前省份,
                mCurrentProvince=areaInfo;

                if (mCurrentAddressInfo.getProvinceId().equals(areaInfo.getAreaId())){//当前省份是
                    //没改动
                }else {
                    // TODO: 2018/2/23 用areaInfo.getAreaId()请求新的城市数据，mCurrentAddressInfo=请求到的数据， 然后设置mCityPicker控件
                    //假设请求到新数据
                    List<AreaInfo> cityList = mCurrentAddressInfo.getCityList();
                    mCityPicker.resetData(cityList);
                    mCurrentCity = AddressUtil.getCurrentCity(mCurrentAddressInfo);
                    mCityPicker.setDefaultByInfo(mCurrentCity);

                }

                /*String provinceText = mProvinceDatas.get(id);
                if (!mCurrentProvince.equals(provinceText)) {
                    mCurrentProvince = provinceText;
                    ArrayList<String> mCityData = new ArrayList<>();
                    cityList = mCurrentAddressInfo.get(id).getChilds();
                    for (int i = 0; i < cityList.size(); i++) {
                        mCityData.add(cityList.get(i).getName());
                    }
                    mCityPicker.resetData(mCityData);
                    mCityPicker.setDefaultByIndex(0);
                    mCurrentCity = mCityData.get(0);

                    ArrayList<String> mCountyData = new ArrayList<>();
                    zoneList = cityList.get(0).getChilds();
                    for (int i = 0; i < zoneList.size(); i++) {
                        mCountyData.add(zoneList.get(i).getName());
                    }
                    mZonePicker.resetData(mCountyData);
                    mZonePicker.setDefaultByIndex(0);
                    mCurrentDistrictName = mCountyData.get(0);

                    ArrayList<String> mStreetData = new ArrayList<>();
                    streetList = zoneList.get(0).getChilds();
                    for (int i = 0; i < streetList.size(); i++) {
                        mStreetData.add(streetList.get(i).getName());
                    }
                    mStreetPicker.resetData(mStreetData);
                    mStreetPicker.setDefaultByIndex(0);
                    mCurrentStreet = mStreetData.get(0);

                }*/
            }

            @Override
            public void selecting(AreaInfo areaInfo) {

            }
        });

        mCityPicker.setOnSelectListener(new MyWheelView.OnSelectListener() {
            @Override
            public void endSelect(AreaInfo areaInfo) {//滑动后选了xx城市
                // TODO: 2018/2/23 ①设置城市、保存当前城市,
                mCurrentCity=areaInfo;

                if (mCurrentAddressInfo.getCityId().equals(areaInfo.getAreaId())){//当前城市是
//                    ToastUtil.showToast(getContext(),"mCityPicker 没改动");
                    Log.i("walke", "endSelect: -----------------mCityPicker 没改动---name="+Thread.currentThread().getName());
                    //没改动
                }else {
                    // TODO: 2018/2/23 用areaInfo.getAreaId()请求新的区域数据，mCurrentAddressInfo=请求到的数据， 然后设置mZonePicker控件
                    //假设请求到新数据
                    List<AreaInfo> zoneList = mCurrentAddressInfo.getZoneList();
                    mZonePicker.resetData(zoneList);
                    mCurrentZone = AddressUtil.getCurrentZone(mCurrentAddressInfo);
                    mZonePicker.setDefaultByInfo(mCurrentZone);

                }
               /* ArrayList<String> mCountyData = new ArrayList<>();
                zoneList = cityList.get(id).getChilds();
                for (int i = 0; i < zoneList.size(); i++) {
                    mCountyData.add(zoneList.get(i).getName());
                }
                mZonePicker.resetData(mCountyData);
                mZonePicker.setDefaultByIndex(0);
                mCurrentDistrictName = mCountyData.get(0);

                streetList = zoneList.get(0).getChilds();
                ArrayList<String> mStreetData = new ArrayList<>();
                for (int i = 0; i < streetList.size(); i++) {
                    mStreetData.add(streetList.get(i).getName());
                }
                mStreetPicker.resetData(mStreetData);
                mStreetPicker.setDefaultByIndex(0);
                mCurrentStreet = mStreetData.get(0);*/

            }

            @Override
            public void selecting(AreaInfo areaInfo) {

            }
        });

        mZonePicker.setOnSelectListener(new MyWheelView.OnSelectListener() {
            @Override
            public void endSelect(AreaInfo areaInfo) {//滑动后选了xx区域
                // TODO: 2018/2/23 ①设置城市、保存当前城市,
                mCurrentZone=areaInfo;

                if (mCurrentAddressInfo.getZoneId().equals(areaInfo.getAreaId())){//当前区域是
                    //没改动
                }else {
                    // TODO: 2018/2/23 用areaInfo.getAreaId()请求新的区域数据，mCurrentAddressInfo=请求到的数据， 然后设置mStreetPicker控件
                    //假设请求到新数据
                    List<AreaInfo> zoneList = mCurrentAddressInfo.getStreetList();
                    mStreetPicker.resetData(zoneList);
                    mCurrentStreet = AddressUtil.getCurrentStreet(mCurrentAddressInfo);
                    mStreetPicker.setDefaultByInfo(mCurrentStreet);

                }
                /*String currentname = zoneList.get(id).getName();
                if (!mCurrentDistrictName.equals(currentname)) {
                    mCurrentDistrictName = currentname;

                    streetList = zoneList.get(id).getChilds();
                    ArrayList<String> mStreetData = new ArrayList<>();
                    for (int i = 0; i < streetList.size(); i++) {
                        mStreetData.add(streetList.get(i).getName());
                    }
                    mStreetPicker.resetData(mStreetData);
                    mStreetPicker.setDefaultByIndex(0);
                    mCurrentStreet = mStreetData.get(0);

                }*/
            }

            @Override
            public void selecting(AreaInfo areaInfo) {

            }
        });


        tvSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMyPickAddressInterface != null) {
                    mMyPickAddressInterface.onSureClick(mCurrentAddressInfo);
                }
            }
        });
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMyPickAddressInterface != null) {
                    mMyPickAddressInterface.onCancelClick();
                }
            }
        });
    }

    /**
     * 读取地址数据，请使用线程进行调用
     *
     * @return
     */
//    List<AddressBean> mCurrentAddressInfo;
//    List<AddressBean.CityChildsBean> cityList = new ArrayList<>();//城市列表
//    List<AddressBean.CityChildsBean.CountyChildsBean> zoneList = new ArrayList<>();//市区列表
//    List<AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean> streetList = new ArrayList<>();//街道列表

    AddressInfo mCurrentAddressInfo;
    List<AreaInfo> provinceList = new ArrayList<>();//省份列表
    List<AreaInfo> cityList = new ArrayList<>();//城市列表
    List<AreaInfo> zoneList = new ArrayList<>();//市区列表
    List<AreaInfo> streetList = new ArrayList<>();//街道列表


    public void setOnTopClicklistener(MyPickAddressInterface myPickAddressInterface) {
        this.mMyPickAddressInterface = myPickAddressInterface;
    }

    public void setData(AddressInfo infos) {
        mCurrentAddressInfo = infos;//
        provinceList=infos.getProvinceList();
        cityList = infos.getCityList();
        zoneList =infos.getZoneList();
        streetList =infos.getStreetList();

        //初始化省份Picker

        mProvincePicker.setData(provinceList);
        mCurrentProvince = AddressUtil.getCurrentProvince(infos);
        mProvincePicker.setDefaultByInfo(mCurrentProvince);

        //初始化城市Picker

        mCityPicker.setData(this.cityList);
        mCurrentCity = AddressUtil.getCurrentCity(infos);
        mCityPicker.setDefaultByInfo(mCurrentCity);

        //初始化区域Picker

        mZonePicker.setData(zoneList);
        mCurrentZone = AddressUtil.getCurrentZone(infos);
        mZonePicker.setDefaultByInfo(mCurrentZone);

        //初始化街道Picker

        mStreetPicker.setData(streetList);
        mCurrentStreet = AddressUtil.getCurrentStreet(infos);
        mStreetPicker.setDefaultByInfo(mCurrentStreet);
    }



    public void onDistory() {
        cityList.clear();
        zoneList.clear();
        streetList.clear();
        mProvincePicker.resetData(new ArrayList<AreaInfo>());
        mZonePicker.resetData(new ArrayList<AreaInfo>());
        mStreetPicker.resetData(new ArrayList<AreaInfo>());
        mZonePicker.resetData(new ArrayList<AreaInfo>());
    }
}
