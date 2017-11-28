package com.tianyan.assistant.ui.home.presenter;

import com.squareup.okhttp.ResponseBody;
import com.tianyan.assistant.base.OnHttpCallBack;
import com.tianyan.assistant.ui.home.Tab1Contact;
import com.tianyan.assistant.ui.home.model.Tab1Model;

import java.io.IOException;

/**
 * Created by Administrator on 2017/10/24.
 */

public class Tab1Presenter implements Tab1Contact.Tab1Presenter {

    private Tab1Contact.Tab1View tab1View;
    private Tab1Contact.Tab1Model tab1Model;
    private String openid;

    public Tab1Presenter(Tab1Contact.Tab1View tab1View) {
        this.tab1View = tab1View;
        tab1Model = new Tab1Model();
    }


    @Override
    public void getBannerData() {
        tab1Model.getBanner(new OnHttpCallBack<ResponseBody>() {
            @Override
            public void onStart() {
                tab1View.showProgress();
            }

            @Override
            public void onComplete() {
                tab1View.hideProgress();
            }

            @Override
            public void onSuccessful(ResponseBody responseBody) {
                try {
                    tab1View.showBannerData(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                tab1View.showMessage(errorMsg);
            }
        });
    }

    @Override
    public void getEnrollData() {
        tab1Model.getEnroll(openid, new OnHttpCallBack<ResponseBody>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccessful(ResponseBody responseBody) {
                try {
                    tab1View.showEnrollData(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                tab1View.showMessage(errorMsg);
            }
        });
    }


    @Override
    public void getEnrollInfo(String openid) {
        this.openid = openid;
    }
}
