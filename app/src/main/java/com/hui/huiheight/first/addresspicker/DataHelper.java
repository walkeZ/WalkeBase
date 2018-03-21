package com.hui.huiheight.first.addresspicker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hui.huiheight.first.addresspicker.bean.AddressInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class DataHelper {
//    private static List<AddressBean> beans;

    public static List<AddressBean> getAddress(Context context) {
        List<AddressBean> beans=new ArrayList<>();
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(
                    "address.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            Gson gson = new Gson();
            //集合type类型(.class)使用(转化)方法
            Type type = new TypeToken<List<AddressBean>>() {
            }.getType();
            beans = gson.fromJson(stringBuilder.toString(), type);
            return beans;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beans;

    }

    public static AddressInfo getAddressInfo(Context context) {
        AddressInfo addressInfo = new AddressInfo();

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(
                    "city_street.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            Gson gson = new Gson();
            String json = stringBuilder.toString();
            addressInfo = gson.fromJson(json, AddressInfo.class);
            return addressInfo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressInfo;

    }
}
