package com.tianyan.assistant.ui.home;

import android.content.Context;

import com.squareup.okhttp.ResponseBody;
import com.tianyan.assistant.base.OnHttpCallBack;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Tab1Contact {

    public interface Tab1View {
        Context getCurContext();

        void showProgress();

        void hideProgress();

        void showBannerData(String json);

        void showEnrollData(String json);

        void showMessage(String message);
    }

    public interface Tab1Presenter {
        void getBannerData();

        void getEnrollData();

        void getEnrollInfo(String openid);
    }

    public interface Tab1Model {
        void getBanner(OnHttpCallBack<ResponseBody> callBack);

        void getEnroll(String openid, OnHttpCallBack<ResponseBody> callBack);
    }


}
