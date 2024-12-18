package com.hui.huiheight.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hui.huiheight.HomeActivity;
import com.hui.huiheight.MainsActivity;
import com.hui.huiheight.R;
import com.hui.huiheight.first.ShopCarActivity;
import com.hui.huiheight.first.addresspicker.AddressPickerActivity;
import com.hui.huiheight.first.chooselocation.ChoiceLocationActivity;
import com.hui.huiheight.first.chooselocation.GetLocationActivity;
import com.hui.huiheight.first.oss.OSSActivity;
import com.hui.huiheight.first.phone.PhoneInfoActivity;
import com.hui.huiheight.first.phone.ScreenShotActivity;
import com.hui.huiheight.first.photo.CutPhonePictureActivity;
import com.hui.huiheight.first.photo.CutPhonePictureActivity2;
import com.hui.huiheight.first.photo.PhonePictureActivity;
import com.hui.huiheight.first.retrofit2.Retrofit2Activity;
import com.hui.huiheight.fragment.adapter.RecyclerViewAdapter;
import com.hui.wheelviewlibrary.WheelViewActivity;
import com.jcodecraeer.xrecyclerview.example.CollapsingToolbarLayoutActivity;
import com.jcodecraeer.xrecyclerview.example.MainActivity2;
import com.jcodecraeer.xrecyclerview.example.MultiHeaderActivity;

import java.util.ArrayList;
import java.util.List;

import walke.base.AppFragment;
import walke.base.TestActivityTest;
import walke.base.tool.SetUtil;
import walke.demolibrary.audio.activity.AudioActivity01;
import walke.demolibrary.audio.activity.AudioActivity02;
import walke.demolibrary.audio.volume.VolumeViewActivity;
import walke.demolibrary.audio.waveform.WaveformActivity;
import walke.demolibrary.completion.CompletionActivity;
import walke.demolibrary.interface1.Demo1Activity;
import walke.demolibrary.interface3.Demo3Activity;
import walke.demolibrary.layoutmanager.TTCardsActivity;
import walke.demolibrary.log.LogActivity;
import walke.demolibrary.movedsp.activitys.FlashScreenActivity;
import walke.demolibrary.picture.BitmapColorActivity;
import walke.demolibrary.pinpu.PinPuActivity;
import walke.demolibrary.pinpu.PinPuActivity2;
import walke.demolibrary.audio.recorder.RecorderActivity;
import walke.demolibrary.vlc1.VLC1Activity;
import walke.demolibrary.wifi.WifiActivity01;
import walke.widget.sunset.SunAnimationActivity;

/**
 * Created by walke.Z on 2017/8/2.
 */
public class ChildFragment extends AppFragment {

    private static ChildFragment mChildFragment;
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private List<AppCompatActivity> mActivities = new ArrayList<>();

    public static ChildFragment getChildFragment(String title, String[] arr) {
        //Java.lang.IllegalStateException: Can't change tag of fragment fendo1MainActivity{531dbce4 index=0x7f0b0054
        // Android:switcher:2131427412:0}: was android:switcher:2131427412:0 now android:switcher:2131427412:1
        //if (mChildFragment==null)
        //mChildFragment=new ChildFragment();
        mChildFragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("strArr", arr);
        mChildFragment.setArguments(args);
        return mChildFragment;
    }

    @Override
    protected int rootLayoutId() {
        List<Class> acts = new ArrayList<>();
        Class<HomeActivity> aClass = HomeActivity.class;
        acts.add(aClass);
        return R.layout.fragment_child;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mTextView = (rootView.findViewById(R.id.fc_text));
        mRecyclerView = (rootView.findViewById(R.id.fc_recyclerView));
        Bundle arguments = getArguments();
        if (arguments != null) {
            String title = arguments.getString("title", "title");
            String[] strArrs = arguments.getStringArray("strArr");
            mTextView.setText(title);
            initActivitys(title);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), SetUtil.arrToList(strArrs));
            mRecyclerView.setAdapter(adapter);

            adapter.setItemClickListener(position -> {
                if (mActivities.size() < 1) {
                    toast("暂未实现，Activity Size is 0");
                    return;
                }
                if (position >= mActivities.size()) {
                    startActivity(new Intent(getActivity(), mActivities.get(mActivities.size() - 1).getClass()));
                } else {
                    startActivity(new Intent(getActivity(), mActivities.get(position).getClass()));
                }
            });
        }
    }

    private void initActivitys(String title) {
        if ("first+child".equals(title)) {// R.array.tabTitles
            mActivities.add(new GetLocationActivity());
            mActivities.add(new ChoiceLocationActivity());
            mActivities.add(new WheelViewActivity());
            mActivities.add(new CutPhonePictureActivity());
            mActivities.add(new CutPhonePictureActivity2());
            mActivities.add(new PhonePictureActivity());
            mActivities.add(new PhoneInfoActivity());
            mActivities.add(new ScreenShotActivity());
            mActivities.add(new Retrofit2Activity());
            mActivities.add(new MainsActivity());
            mActivities.add(new TestActivityTest());
            mActivities.add(new SunAnimationActivity());
            mActivities.add(new Demo1Activity());//Java多态接口动态加载实例
            mActivities.add(new ShopCarActivity());
            mActivities.add(new AddressPickerActivity());
            mActivities.add(new OSSActivity());

        } else if ("demo".equals(title)) {
            mActivities.add(new Demo1Activity());
            mActivities.add(new Demo3Activity());
            mActivities.add(new CompletionActivity());
            mActivities.add(new BitmapColorActivity());
            mActivities.add(new TTCardsActivity());
            mActivities.add(new PinPuActivity());
            mActivities.add(new PinPuActivity2());
            mActivities.add(new AudioActivity01());
            mActivities.add(new AudioActivity02());
            mActivities.add(new WaveformActivity());
            mActivities.add(new RecorderActivity());
            mActivities.add(new VolumeViewActivity());
            mActivities.add(new FlashScreenActivity());
            mActivities.add(new WifiActivity01());
            mActivities.add(new LogActivity());
            mActivities.add(new VLC1Activity());
//            mActivities.add(new GlideActivity());
        } else if ("views".equals(title)) {

        } else if ("消息".equals(title)) {
            mActivities.add(new MainActivity2());
            mActivities.add(new CollapsingToolbarLayoutActivity());
            mActivities.add(new MultiHeaderActivity());
        } else {

        }
    }

    @Override
    protected void initData() {
        askPermission();
    }

    List<String> permissions = new ArrayList<>();

    /**
     * https://blog.csdn.net/idlegao/article/details/128966705
     *
     * @return
     */
    private boolean askPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;
        // 音频权限
        int RECORD_AUDIO = getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO);
        if (RECORD_AUDIO != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        // 文件读写
        int sdCardWrite = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int sdCardRead = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (sdCardWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (sdCardRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.isEmpty())
            return false;
        requestPermissions(permissions.toArray(new String[permissions.size()]), 1);
        return true;
    }
}
