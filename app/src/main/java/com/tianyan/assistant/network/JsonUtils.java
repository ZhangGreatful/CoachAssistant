package com.tianyan.assistant.network;

import com.tianyan.assistant.model.AutoPlayInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class JsonUtils {

    public static String parseMsg(String json) {
        String msg = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            msg = jsonObject.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
    public static String parseOpenId(String json) {
        String openid = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            openid = jsonObject.optString("openid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return openid;
    }

    public static List<AutoPlayInfo> parseBanner(String json) {
        List<AutoPlayInfo> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.optJSONObject(i);
                AutoPlayInfo info = new AutoPlayInfo();
                info.setImageId(Integer.valueOf(obj.optString("wid")));
                info.setTitle(obj.optString("biaoti"));
                info.setImageUrl(obj.optString("img"));
                info.setAdLinks(obj.optString("url"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
