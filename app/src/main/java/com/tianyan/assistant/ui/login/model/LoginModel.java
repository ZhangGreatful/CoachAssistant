package com.tianyan.assistant.ui.login.model;

import android.content.Context;
import android.util.ArrayMap;

import com.tianyan.assistant.base.BaseEntity;
import com.tianyan.assistant.base.BaseObserver;
import com.tianyan.assistant.base.GlobalField;
import com.tianyan.assistant.base.OnHttpCallBack;
import com.tianyan.assistant.network.ApiService;
import com.tianyan.assistant.network.RetrofitUtils;
import com.tianyan.assistant.ui.login.LoginContact;
import com.tianyan.assistant.utils.SignUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/10/25.
 */

public class LoginModel implements LoginContact.LoginModel {
    @Override
    public void Login(String username, String password, final OnHttpCallBack<ResponseBody> callBack) {


        RetrofitUtils.newInstence(GlobalField.BASS_URL, GlobalField.LOG_IN)
                .create(ApiService.class)
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onStart();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callBack.onSuccessful(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }
                });

    }

    @Override
    public void getMineInfo(Context context, ArrayMap map, final OnHttpCallBack<ResponseBody> callBack) {
        RetrofitUtils.newInstence(GlobalField.BASS_URL, GlobalField.MY_SELF)
                .create(ApiService.class)
                .getMineInfo(SignUtil.getSign(context, map))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    protected void onSuccees(BaseEntity<ResponseBody> t) throws Exception {

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
