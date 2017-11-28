package com.tianyan.assistant.ui.home.model;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;
import com.tianyan.assistant.base.GlobalField;
import com.tianyan.assistant.base.OnHttpCallBack;
import com.tianyan.assistant.network.ApiService;
import com.tianyan.assistant.network.RetrofitUtils;
import com.tianyan.assistant.ui.home.Tab1Contact;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/10/24.
 */

public class Tab1Model implements Tab1Contact.Tab1Model {


    @Override
    public void getBanner(final OnHttpCallBack<ResponseBody> callBack) {
        RetrofitUtils.newInstence(GlobalField.BASS_URL, GlobalField.COACH)
                .create(ApiService.class)
                .getBanner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        callBack.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        callBack.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "MSG=============" + e.getMessage().toString());
                        callBack.onFaild(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callBack.onSuccessful(responseBody);
                    }
                });
    }

    @Override
    public void getEnroll(String openid, final OnHttpCallBack<ResponseBody> callBack) {
        RetrofitUtils.newInstence(GlobalField.BASS_URL, GlobalField.ZHAO_SHENG)
                .create(ApiService.class)
                .getEnroll(openid, "ms")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callBack.onSuccessful(responseBody);
                    }
                });
    }
}
