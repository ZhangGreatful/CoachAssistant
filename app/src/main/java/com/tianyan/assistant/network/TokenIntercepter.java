package com.tianyan.assistant.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

///**
// * Created by Administrator on 2016/7/7 0007.
// */
public class TokenIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        每次请求,都加一个头进去Authorization: token+值
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Response response = chain.proceed(builder.build());
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == 401 || response.code() == 403) {
            throw new IOException("未经授权的!限制是每分钟10次");
        } else {
            throw new IOException("响应码:" + response.code());
        }
    }
}
