package com.tianyan.assistant.ui.login.presenter;

import android.content.Context;
import android.util.ArrayMap;

import com.squareup.okhttp.ResponseBody;
import com.tianyan.assistant.base.OnHttpCallBack;
import com.tianyan.assistant.ui.login.LoginContact;
import com.tianyan.assistant.ui.login.model.LoginModel;

import java.io.IOException;

/**
 * Created by Administrator on 2017/10/25.
 */

public class LoginPresenter implements LoginContact.LoginPresenter {

    private LoginContact.LoginView loginView;
    private LoginContact.LoginModel loginModel;

    public LoginPresenter(LoginContact.LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    @Override
    public void getLoginData(String tel, String pwd) {
        loginModel.Login(tel, pwd, new OnHttpCallBack<ResponseBody>() {
            @Override
            public void onStart() {
                loginView.showProgress();
            }

            @Override
            public void onComplete() {
                loginView.hideProgress();
            }

            @Override
            public void onSuccessful(ResponseBody responseBody) {
                try {
                    loginView.showLoginData(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                loginView.showMessage(errorMsg);
            }
        });
    }

    @Override
    public void getMineInfo(Context context, String openid) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("openid", openid);
        loginModel.getMineInfo(context, map, new OnHttpCallBack<ResponseBody>() {
            @Override
            public void onStart() {
                loginView.showProgress();
            }

            @Override
            public void onComplete() {
                loginView.hideProgress();
            }

            @Override
            public void onSuccessful(ResponseBody responseBody) {
                try {
                    loginView.showMineData(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                loginView.showMessage(errorMsg);
            }
        });
    }
}
