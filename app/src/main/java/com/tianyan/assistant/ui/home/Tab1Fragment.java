package com.tianyan.assistant.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianyan.assistant.R;
import com.tianyan.assistant.base.BaseFragment;
import com.tianyan.assistant.model.AutoPlayInfo;
import com.tianyan.assistant.network.JsonUtils;
import com.tianyan.assistant.ui.home.presenter.Tab1Presenter;
import com.tianyan.assistant.utils.MLog;
import com.tianyan.assistant.widget.AutoPlayingViewPager;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class Tab1Fragment extends BaseFragment implements View.OnClickListener, Tab1Contact.Tab1View {

    private LinearLayout lianche, banche, yuyin, kaoshi, xinde, miji;
    private LinearLayout mingpian, xueche, keshi, kanjia, shixue, zhan;
    private LinearLayout kongjian;
    private TextView text, dagang;
    private AutoPlayingViewPager mAutoPlayingViewPager;
    private List<AutoPlayInfo> mAutoPlayInfoList;
    private Tab1Presenter tab1Presenter;


    @Override
    public int getLayoutResId() {
        return R.layout.tab1_fragment;
    }

    @Override
    public void finishCreateView(Bundle state) {
        tab1Presenter = new Tab1Presenter(this);
        kongjian = (LinearLayout) getActivity().findViewById(R.id.kongjian);
        kongjian.setOnClickListener(this);

        dagang = (TextView) getActivity().findViewById(R.id.dagang);
        mAutoPlayingViewPager = (AutoPlayingViewPager) getActivity().findViewById(R.id.auto_play_viewpager);
        text = (TextView) getActivity().findViewById(R.id.qiang);
//        教学部分
        lianche = (LinearLayout) getActivity().findViewById(R.id.lianche);
        banche = (LinearLayout) getActivity().findViewById(R.id.banche);
        yuyin = (LinearLayout) getActivity().findViewById(R.id.yuyin);
        kaoshi = (LinearLayout) getActivity().findViewById(R.id.anpai);
        xinde = (LinearLayout) getActivity().findViewById(R.id.xinde);
        miji = (LinearLayout) getActivity().findViewById(R.id.kaoshi);
        zhan = (LinearLayout) getActivity().findViewById(R.id.zhan);

        dagang.setOnClickListener(this);
        zhan.setOnClickListener(this);
        text.setOnClickListener(this);
        lianche.setOnClickListener(this);
        banche.setOnClickListener(this);
        yuyin.setOnClickListener(this);
        kaoshi.setOnClickListener(this);
        xinde.setOnClickListener(this);
        miji.setOnClickListener(this);

//        招生部分
        mingpian = (LinearLayout) getActivity().findViewById(R.id.mingpian);
        xueche = (LinearLayout) getActivity().findViewById(R.id.xueche);
        keshi = (LinearLayout) getActivity().findViewById(R.id.keshi);
        kanjia = (LinearLayout) getActivity().findViewById(R.id.kanjia);
        shixue = (LinearLayout) getActivity().findViewById(R.id.shixue);
        mingpian.setOnClickListener(this);
        xueche.setOnClickListener(this);
        keshi.setOnClickListener(this);
        kanjia.setOnClickListener(this);
        shixue.setOnClickListener(this);
        initData();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initData() {
        String openid = "d63bdc73f1509283f8e01092fd2fdd1d";
        tab1Presenter.getEnrollInfo(openid);
        tab1Presenter.getBannerData();
        tab1Presenter.getEnrollData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public Context getCurContext() {
        return getActivity();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showBannerData(String json) {
        MLog.e("banner", json);
        String msg = JsonUtils.parseMsg(json);
        if (msg.equals("1")) {
            mAutoPlayInfoList = JsonUtils.parseBanner(json);
            mAutoPlayingViewPager.initialize(mAutoPlayInfoList).build();
            mAutoPlayingViewPager.setOnPageItemClickListener(onPageItemClickListener);
            mAutoPlayingViewPager.startPlaying();
        }
    }

    @Override
    public void showEnrollData(String json) {
        MLog.e("enroll", json);
    }

    @Override
    public void showMessage(String message) {
        MLog.e("error", message);

    }

    private AutoPlayingViewPager.OnPageItemClickListener onPageItemClickListener = new AutoPlayingViewPager.OnPageItemClickListener() {

        @Override
        public void onPageItemClick(int position, String adLink) {
            // 直接返回链接,使用WebView加载
            if (!TextUtils.isEmpty(adLink)) {
                //链接存在时才进行下一步操作,当然，这只是简单判断,这个字符串不是正确链接,则需要加上正则表达式判断。
//                Intent intent = new Intent(HomeActivity.this,
//                        WebNewActivity.class);
//                intent.putExtra(Keys.WebURL, adLink);
//                startActivity(intent);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MLog.e("onPause==========");
        if (mAutoPlayingViewPager != null) {
            mAutoPlayingViewPager.stopPlaying();
        }
    }
}
