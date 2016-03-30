package com.hg.www.selller.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 同步商品信息的后台服务
 */
public class CommodityService extends BasicService {
    public static final String TAG = CommodityService.class.getSimpleName();
    private static final int count = 100;

    public static void startService(String action, String data) {
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        intent.putExtra("data", data);
        intent.setAction(action);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        GlobalContext.getInstance().stopService(intent);
    }
}
