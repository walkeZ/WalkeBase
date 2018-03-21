package com.hui.huiheight.first.retrofit2;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by walke.Z on 2017/9/4.
 */

public interface APIService {

    @GET("http://a.meidebi.com/new.php/Share-getslide?version=3.0.8&devicetoken=866609022244983&devicetype=2")
    Call<AdvData> loadSpecialUsers();

    @GET("http://a.meidebi.com/{tag}/Share-allhotlist?version=3.0.8&devicetoken=866609022244983&limit=20&p=2&type=tian&devicetype=2")
    Call<ContentData> loadDatas(@Path("tag") String tag);

    @GET("http://a.meidebi.com/new.php/Share-allhotlist")
    Call<ContentData> loadGoodsDatas(@Query("sort") String sort);

}
