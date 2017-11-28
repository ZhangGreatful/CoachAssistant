package com.tianyan.assistant.network;


import android.util.ArrayMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by Administrator on 2017/10/18.
 */

public interface ApiService {

    @POST("banner")
    Observable<ResponseBody> getBanner();

    @FormUrlEncoded
    @POST("getZhaoshengList")
    Observable<ResponseBody> getEnroll(@Field("openid") String openid, @Field("type") String type);

    @FormUrlEncoded
    @POST("entrance_Login")
    Observable<ResponseBody> login(@Field("tel") String tel, @Field("password") String pwd);

    @GET("getUserInfoByOpenid")
    Observable<ResponseBody> getMineInfo(@QueryMap ArrayMap<String,String> map);

}
