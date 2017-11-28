package com.tianyan.assistant.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xialo on 2016/7/25.
 */
public class SharedPreferencesUtil {

    private static final String Mine = "mine";
    public static final String FIRST_OPEN = "first_open";
    public static final String OPEN_ID = "openid";
    private Context mContext;

    public SharedPreferencesUtil(Context context) {
        this.mContext = context;
    }

    public static Boolean getFirstOpen(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                Mine, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(FIRST_OPEN, true);
        return result;
    }

    public static void putFirstOpen(Context context, Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                Mine, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(FIRST_OPEN, strData);
        editor.commit();
    }

    public void setOpenId(String openid) {
        SharedPreferences activityPreferences = mContext.getSharedPreferences(
                Mine, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(OPEN_ID, openid);
        editor.commit();
    }

    public String getOpenId() {
        SharedPreferences setPreferences = mContext.getSharedPreferences(
                Mine, Context.MODE_PRIVATE);
        String openid = setPreferences.getString(OPEN_ID, "");
        return openid;
    }

}
