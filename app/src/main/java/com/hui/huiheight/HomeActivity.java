package com.hui.huiheight;

import android.Manifest;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hui.huiheight.config.Contants;
import com.hui.huiheight.config.Datas;
import com.hui.huiheight.fragment.ChildFragment;
import com.hui.huiheight.fragment.MineFragment;
import com.hui.huiheight.fragment.NewsFragment;
import com.hui.huiheight.fragment.ViewsFragment;
import com.hui.huiheight.util.DialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import walke.base.activity.AppActivity;
import walke.base.tool.FileUtils;
import walke.base.tool.PermissionUtils;
import walke.base.tool.SharepreUtil;


public class HomeActivity extends AppActivity {

    private RadioGroup tabRadioGroup;
    private RadioButton rbFirst, rbViews,rbNews,rbMine;
    private ImageView ivLaunch,ivNull;
    /**当新进入HomeActivity时需要(true)加载HomeInfo,在InfoFragment中加载后设置为不需要，当HomeActivity销毁时标识重置(需要加载HomeInfo)*/
    public boolean hasLoadHomeInfo =true;
    private ViewPager mViewPager;


    @Override
    protected int rootLayoutId() {

        return R.layout.activity_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        tabRadioGroup = (RadioGroup) findViewById(R.id.ah_radioGroup);
        rbFirst = (RadioButton) findViewById(R.id.ah_rbFirst);
        rbViews = (RadioButton) findViewById(R.id.ah_rbViews);
        rbNews = (RadioButton) findViewById(R.id.ah_rbNews);
        rbMine = (RadioButton) findViewById(R.id.ah_rbMine);

        mViewPager = (ViewPager) findViewById(R.id.ah_viewPager);

        ivLaunch = ((ImageView) findViewById(R.id.ah_launch));


        String[] permissionSDCard = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        boolean b = PermissionUtils.checkPermissionSetLack(this, permissionSDCard);

        if (b) {
            requestPermissions(permissionSDCard, Contants.PERMISSION_SDCARD_REQUEST_CODE);
            return;
        }

        // 判断存储卡是否可用，创建文件夹
        boolean sDcardAvailable = FileUtils.isSDcardAvailable();
        logI("sDcardAvailable : "+sDcardAvailable+"  -----> sdcard Permission b: "+b);
        if (sDcardAvailable) {
            File file = new File(Contants.APP_LOCATION);
            boolean exists = file.exists();
            logI("sDcardAvailable : "+sDcardAvailable+"   ----------> exists: "+exists);
            if (!exists){
                file.mkdir();
            }
        }


    }


    @Override
    protected void initData() {

        //rbFirst.setChecked(true);
        rbNews.setChecked(true);
        boolean firstOpen = SharepreUtil.getBoolean(this, Contants.FIRST_OPEN, false);
        if (firstOpen) {//启动应用第一次进入HomeActivity，显示公司logo
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivLaunch.setVisibility(View.GONE);
                }
            }, 1700);

        } else {
            ivLaunch.setVisibility(View.GONE);
        }

        final List<Fragment> baseFragments = new ArrayList<>();
        ChildFragment childFragment = ChildFragment.getChildFragment("first+child", Datas.FIRST_SKILLS);
        ViewsFragment viewsFragment = new ViewsFragment();
        NewsFragment newsFragment = new NewsFragment();
        MineFragment mineFragment = new MineFragment();

        baseFragments.add(childFragment);
        baseFragments.add(viewsFragment);
        baseFragments.add(newsFragment);
        baseFragments.add(mineFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return baseFragments.get(position);
            }

            @Override
            public int getCount() {
                return baseFragments.size();
            }
        });

        tabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                switch (arg1) {
                    case R.id.ah_rbFirst:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.ah_rbViews:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.ah_rbNews:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.ah_rbMine:
                        mViewPager.setCurrentItem(3, false);

//                        ivNull.setVisibility(View.GONE);
                        DialogUtil.showAgreeDialog(HomeActivity.this,"333",
                                Contants.TEST_CONTENT);
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(2, false);//以消息tab为默认显示  view//1

    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onStop() {
        super.onStop();
        //hasLoadHomeInfo =false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
