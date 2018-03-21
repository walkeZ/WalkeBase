package com.hui.huiheight.first.retrofit2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hui.huiheight.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import walke.base.activity.TitleActivity;
import walke.base.adapter.SimpleRecyclerViewAdapter;
import walke.base.widget.TitleLayout;


/**
 * Created by walke.Z on 2017/9/1.
 * 参考：http://www.jianshu.com/p/3e13e5d34531   简书(全)
 *      http://www.open-open.com/lib/view/open1453552147323.html
 *      http://blog.csdn.net/u014165119/article/details/49280779
 *      http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3460.html
 *      http://blog.csdn.net/qq_17766199/article/details/49946429
 */

public class Retrofit2Activity extends TitleActivity {
    private RecyclerView mRecyclerView;
    private APIService mAPIService;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_retrofit2;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RetrofitActivity");
        mRecyclerView = ((RecyclerView) findViewById(R.id.rvRetrofit2Simple));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nuuneoi.com/base/")
                .addConverterFactory(GsonConverterFactory.create())//// 使用Gson作为数据转换器
                .build();
        mAPIService = retrofit.create(APIService.class);

    }

    public void click(View v){
        toast("click");
        Call<AdvData> usersCall = mAPIService.loadSpecialUsers();
        usersCall.enqueue(new Callback<AdvData>() {
            @Override
            public void onResponse(Response<AdvData> response) {
                List<AdvData.DataBean> data = response.body().getData();
                SimpleRecyclerViewAdapter<AdvData.DataBean> adapter = new SimpleRecyclerViewAdapter<>(data);
                mRecyclerView.setAdapter(adapter);
                logI(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                logE(t.getMessage());
            }
        });
    }
    public void test(View v){
        toast("test");
        Call<ContentData> usersCall = mAPIService.loadDatas("new.php");
        usersCall.enqueue(new Callback<ContentData>() {
            @Override
            public void onResponse(Response<ContentData> response) {
                ContentData.DataBean data = response.body().getData();
                List<ContentData.DataBean.LinklistBean> linklist = data.getLinklist();
                ContentDataAdapter adapter = new ContentDataAdapter(Retrofit2Activity.this, linklist);
                mRecyclerView.setAdapter(adapter);
                logI(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                logE(t.getMessage());
            }
        });
    }
    public void test2(View v){
        toast("test2");

        Call<ContentData> usersCall = mAPIService.loadGoodsDatas("version=3.0.8&devicetoken=866609022244983&limit=20&p=2&type=tian&devicetype=2");
        usersCall.enqueue(new Callback<ContentData>() {
            @Override
            public void onResponse(Response<ContentData> response) {
                ContentData.DataBean data = response.body().getData();
                List<ContentData.DataBean.LinklistBean> linklist = data.getLinklist();
                ContentDataAdapter adapter = new ContentDataAdapter(Retrofit2Activity.this, linklist);
                mRecyclerView.setAdapter(adapter);
                logI(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                logE(t.getMessage());
            }
        });
    }




}
