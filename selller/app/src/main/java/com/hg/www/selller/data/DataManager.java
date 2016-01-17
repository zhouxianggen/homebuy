package com.hg.www.selller.data;

import android.content.SharedPreferences;

import com.hg.www.selller.MyApplication;
import com.hg.www.selller.define.Commodity;

public class DataManager {
    private static final String COMMODITY_PREFERENCES = "COMMODITY_PREFERENCES";
    protected MyApplication mContext;

    public DataManager(MyApplication context) {
        mContext = context;
    }

    public Commodity GetCommodity(String id) {
        Commodity commodity = new Commodity();
        if (!id.isEmpty()) {
            SharedPreferences preferences = mContext.getSharedPreferences(COMMODITY_PREFERENCES, mContext.MODE_PRIVATE);
            String str = preferences.getString(id, "");
            if (!str.isEmpty()) {
                commodity.parseFromString(preferences.getString(id, ""));
            }
        }
        return commodity;
    }
}
