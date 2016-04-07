package com.hg.www.selller.service;

import android.content.Context;
import android.content.Intent;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.define.Message;

public class MessageService extends BasicService {
    public static final String TAG = MessageService.class.getSimpleName();
    private static final int count = 100;

    public static void startService() {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().stopService(intent);
    }
}