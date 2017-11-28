package com.tianyan.assistant.utils;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author john
 */
public class SignUtil {

    public static ArrayMap<String, String> getSign(Context context, ArrayMap<String, String> map) {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();

        String sign = "";
        String time = getTimestamp();
        map.put("timestamp", time);
        ArrayList<String> keyList = new ArrayList<String>();
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            keyList.add(key);
        }
        Collections.sort(keyList);// 排序
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyList.size(); i++) {
            String value = map.get(keyList.get(i));
            try {
                if (!value.contains("{"))
                    value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append(keyList.get(i)).append("=").append(value).append("&");
            arrayMap.put(keyList.get(i), value);
        }

        String content = sb.toString().substring(0, sb.length() - 1);
        try {
            InputStream inPrivate = context.getResources().getAssets()
                    .open("pkcs8_private_key.pem");
            String stringFromInputStream = RSAEncrypt
                    .getStringFromInputStream(inPrivate);
            sign = RSASignature.sign(content, stringFromInputStream);
            sign = URLEncoder.encode(sign, "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String string = new StringBuffer().append(content).append("&sign=").append(sign)
                .toString();
        arrayMap.put("sign", sign);

        Log.d("TAG", "SIGN=============" + string);
        Log.d("TAG", "SIGN=============" + arrayMap.get("timestamp"));
        Log.d("TAG", "SIGN=============" + arrayMap.get("openid"));
        Log.d("TAG", "SIGN=============" + arrayMap.get("sign"));
        return arrayMap;
    }

    public static ArrayList<String> getSignList(Context context,
                                                HashMap<String, String> map) {
        ArrayList<String> list = new ArrayList<String>();
        String sign = "";
        String time = getTimestamp();
        map.put("timestamp", time);
        ArrayList<String> keyList = new ArrayList<String>();
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            keyList.add(key);
        }
        Collections.sort(keyList);// 排序
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyList.size(); i++) {
            String value = map.get(keyList.get(i));
            try {
                if (!value.contains("{"))
                    value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append(keyList.get(i)).append("=").append(value).append("&");
        }

        String content = sb.toString().substring(0, sb.length() - 1);
        try {
            InputStream inPrivate = context.getResources().getAssets()
                    .open("pkcs8_private_key.pem");
            String stringFromInputStream = RSAEncrypt
                    .getStringFromInputStream(inPrivate);
            sign = RSASignature.sign(content, stringFromInputStream);
            //sign = URLEncoder.encode(sign, "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        list.add(time);
        list.add(sign);
        return list;
    }

    public static HashMap<String, String> PostSign(Context context,
                                                   HashMap<String, String> map) {
        String sign = "";
        String time = getTimestamp();
        map.put("timestamp", time);
        ArrayList<String> keyList = new ArrayList<String>();
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            keyList.add(key);
        }
        Collections.sort(keyList);// 排序
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyList.size(); i++) {
            String value = map.get(keyList.get(i));
            try {
                if (!value.contains("{"))
                    value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append(keyList.get(i)).append("=").append(value).append("&");
        }

        String content = sb.toString().substring(0, sb.length() - 1);
        try {
            InputStream inPrivate = context.getResources().getAssets()
                    .open("pkcs8_private_key.pem");
            String stringFromInputStream = RSAEncrypt
                    .getStringFromInputStream(inPrivate);
            sign = RSASignature.sign(content, stringFromInputStream);
            // sign = URLEncoder.encode(sign, "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("sign", sign);
        return map;
    }

    public static String getTimestamp() {
        String timeStamp = "";
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts2 = tsLong.toString();
        try {
            timeStamp = URLEncoder.encode(ts2, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeStamp;
    }
}
