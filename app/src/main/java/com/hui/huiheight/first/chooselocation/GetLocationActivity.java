package com.hui.huiheight.first.chooselocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/2.
 * http://blog.jobbole.com/60564/
 */

public class GetLocationActivity extends TitleActivity implements LocationListener {
    private LocationManager locationManager;
    private TextView tvLocation;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_get_location;
    }


    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("获取经纬度");
        tvLocation = (TextView) findViewById(R.id.agl_location);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            toast("缺少权限，获取当前经纬度失败");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},123);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
    }

    @Override
    public void onLocationChanged(final Location location) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("walke: ", " GetLocationActivity:  run:-------> Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
                tvLocation.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            }
        },2000);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        logI("status"+extras);
    }

    @Override
    public void onProviderEnabled(String provider) {
        logI("onProviderEnabled    provider= "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        logI("onProviderDisabled    provider= "+provider);
    }
}
