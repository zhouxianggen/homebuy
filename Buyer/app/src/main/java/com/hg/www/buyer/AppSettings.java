package com.hg.www.buyer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {
    public static String getBuyerServerAddress() {
        return "http://54.149.127.185/buyer";
    }

    public static String getSellersServerAddress() {
        return "http://54.149.127.185/buyer/sellers";
    }

    public static String getCommoditiesServerAddress() {
        return "http://54.149.127.185/seller/commodities";
    }

    public static String getOrdersServerAddress() {
        return "http://54.149.127.185/buyer/orders";
    }
}
