package com.hg.www.selller;

import android.app.Application;
import android.provider.ContactsContract;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hg.www.selller.data.api.DataExchangeInterface;
import com.hg.www.selller.data.define.JsonSerializable;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 全局环境
 */
public class GlobalContext extends Application {
    private static GlobalContext mContext;
    private static String mServer = "";
    private static HashMap<String, String> mPathTimestamps = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static GlobalContext getInstance() {
        return mContext;
    }

    public String getUri(String path) {
        String timestamp = mPathTimestamps.get(path);
        if (timestamp == null) {
            timestamp = "";
        }
        return mServer + path + "?timestamp=" + timestamp;
    }

    public void setPathTimestamp(String path, String timestamp) {
        mPathTimestamps.put(path, timestamp);
    }
}
