package com.tianyan.assistant.ui.login;

import android.content.Context;
import android.util.ArrayMap;

import com.tianyan.assistant.base.OnHttpCallBack;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/10/25.
 */

public class LoginContact {

    public interface LoginView {
        Context getCurContext();

        void showProgress();

        void hideProgress();

        void showLoginData(String json);

        void showMineData(String json);

        void showMessage(String message);
    }

    public interface LoginPresenter {

        void getLoginData(String tel, String pwd);

        void getMineInfo(Context context, String openid);
    }

    public interface LoginModel {
        void Login(String username, String password, OnHttpCallBack<ResponseBody> callBack);

        void getMineInfo(Context context, ArrayMap map, OnHttpCallBack<ResponseBody> callBack);
    }
}
