package com.tianyan.assistant.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianyan.assistant.R;
import com.tianyan.assistant.base.BaseActivity;
import com.tianyan.assistant.network.JsonUtils;
import com.tianyan.assistant.ui.login.presenter.LoginPresenter;
import com.tianyan.assistant.utils.MLog;
import com.tianyan.assistant.utils.SharedPreferencesUtil;
import com.tianyan.assistant.utils.ToastShow;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/18.
 */

public class LoginActivity extends BaseActivity implements LoginContact.LoginView {

    @BindView(R.id.username)
    EditText userEdit;
    @BindView(R.id.password)
    EditText passEdit;
    @BindView(R.id.forget)
    TextView forgetTxt;
    @BindView(R.id.register)
    TextView registerTxt;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.wechat)
    LinearLayout wechat;
    @BindView(R.id.qq)
    LinearLayout qq;
    @BindView(R.id.weibo)
    LinearLayout weibo;

    private SharedPreferencesUtil sp;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sp = new SharedPreferencesUtil(this);
        loginPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.login)
    public void setLogin() {
        String username = userEdit.getText().toString().trim();
        String password = passEdit.getText().toString().trim();
        if (username == null || username.equals("")) {
            ToastShow.getInstance(this).toastShow("请输入手机号");
            return;
        } else if (username.length() < 11) {
            ToastShow.getInstance(this).toastShow("请输入正确的手机号");
            return;
        } else if (password == null || password.equals("")) {
            ToastShow.getInstance(this).toastShow("请输入密码");
            return;
        }
        loginPresenter.getLoginData(username, password);
    }

    @Override
    public Context getCurContext() {
        return mActivity;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoginData(String json) {
        MLog.e(json);
        String msg = JsonUtils.parseMsg(json);
        if (msg.equals("1")) {//登录成功
            String openid = JsonUtils.parseOpenId(json);
            sp.setOpenId(openid);
            loginPresenter.getMineInfo(this, openid);
        } else {
            ToastShow.getInstance(this).toastShow("登录失败，请检查用户名或密码是否正确");
        }
    }

    @Override
    public void showMineData(String json) {
        MLog.e(json);
    }

    @Override
    public void showMessage(String message) {
        MLog.e(message);
    }
}
