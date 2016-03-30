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

    public static int getUpdateCategoryInterval() {
        String key = GlobalContext.getInstance().getResources().getString(R.string.PREF_UPDATE_CATEGORY_INTERVAL);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(GlobalContext.getInstance());
        int interval = Integer.parseInt(prefs.getString(key, "10"));
        return interval;
    }

    public static String getCategoriesServerAddress() {
        return "http://54.149.127.185/categories";
    }

    public static String getCategoryServerAddress() {
        return "http://54.149.127.185/category";
    }

    public static String getCommodityServerAddress() {
        return "http://54.149.127.185/commodity";
    }
}
