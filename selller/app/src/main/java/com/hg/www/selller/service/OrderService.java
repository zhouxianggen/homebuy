package com.hg.www.selller.service;

import android.content.Intent;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;

public class OrderService extends BasicService {
    public static final String TAG = OrderService.class.getSimpleName();
    public static final String ACTION_GET = "com.hg.www.ORDER_SERVICE_ACTION_GET";
    public static final String ACTION_UPDATE = "com.hg.www.ORDER_SERVICE_ACTION_UPDATE";

    public static void startService() {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        intent.setAction(ACTION_GET);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    private int getUpdateInterval() {
        return AppSettings.getUpdateOrderInterval();
    }

    private void onActionGet() {
    }
}
