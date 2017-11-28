package com.tianyan.assistant;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tianyan.assistant.ui.home.MainActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class MyApplication extends Application {

    private static Context sAppContext;
    private static MyApplication instance;
    private List<Activity> activityList = new LinkedList<Activity>();
    private static final String TAG = "MyApplication";
    private static final String RealmName = "dalong.realm";

    // 单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 内存泄漏
     */
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public MyApplication() {
        sAppContext = getAppContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();

//        initCallgraphy();
        sAppContext = this;
        initLeakCanary();
    }


    public static Context getAppContext() {
        return sAppContext;
    }



    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = installLeakCanary();
        }
    }

    /**
     * release版本使用此方法
     */
    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }


    // 遍历所有Activity并finish
    public void exitNoMain() {
        for (Activity activity : activityList) {
            if (activity instanceof MainActivity) {

            } else {
                activity.finish();
            }
        }
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 添加Activity到容器中
    public void clearActivity() {
        activityList.clear();
    }
}
