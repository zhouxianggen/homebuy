package com.hg.www.selller.service;

import android.content.Intent;

import com.hg.www.selller.GlobalContext;

public class OrderService extends BasicService {
    public static final String TAG = OrderService.class.getSimpleName();
    private static final int count = 100;

    public static void startService(String action, String data) {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        intent.putExtra("data", data);
        intent.setAction(action);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().stopService(intent);
    }
}
