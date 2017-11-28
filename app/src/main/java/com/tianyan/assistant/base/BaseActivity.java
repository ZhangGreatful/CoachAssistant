package com.tianyan.assistant.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.tianyan.assistant.MyApplication;
import com.tianyan.assistant.utils.GeneralUtil;
import com.tianyan.assistant.utils.KeyBoardUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/9.
 */

public abstract class BaseActivity extends AutoLayoutActivity {

    private Unbinder bind;
    public Activity mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        bind = ButterKnife.bind(this);
        mActivity = this;
        MyApplication.getInstance().addActivity(this);
        initView(savedInstanceState);
    }
    protected abstract int getLayout();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 点击空白处影藏输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (GeneralUtil.isHideInput(view, ev)) {
                KeyBoardUtils.HideSoftInput(view.getWindowToken(),
                        getApplicationContext());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
