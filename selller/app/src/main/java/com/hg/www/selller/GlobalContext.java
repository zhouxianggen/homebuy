package com.hg.www.selller;

import android.app.Application;

import com.hg.www.selller.data.db.DbHelper;

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
        DbHelper.fackData();
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
