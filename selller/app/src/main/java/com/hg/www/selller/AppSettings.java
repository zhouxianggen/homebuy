package com.hg.www.selller;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {
    public static int getUpdateOrderInterval() {
        String key = GlobalContext.getInstance().getResources().getString(R.string.PREF_UPDATE_ORDER_INTERVAL);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GlobalContext.getInstance());
        int interval = Integer.parseInt(prefs.getString(key, "10"));
        return interval;
    }

    public static int getUpdateExpressmanMessageInterval() {
        String key = GlobalContext.getInstance().getResources().getString(R.string.PREF_UPDATE_EXPRESSMAN_MESSAGE_INTERVAL);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GlobalContext.getInstance());
        int interval = Integer.parseInt(prefs.getString(key, "10"));
        return interval;
    }
}
