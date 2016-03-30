package com.hg.www.selller.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hg.www.selller.GlobalContext;

import java.util.Calendar;


public class BasicService extends Service {
    public static final String TAG = BasicService.class.getSimpleName();
    public static final String ACTION_GET = "com.hg.www.ACTION_GET";
    public static final String ACTION_POST = "com.hg.www.ACTION_POST";
    public static final String ACTION_UPDATE = "com.hg.www.ACTION_UPDATE";
    public static final String RESP_STATUS_OK = "ok";

    protected static void clearAlarm() {
        Log.d(TAG, "clear unread block");

        AlarmManager am = (AlarmManager) GlobalContext.getInstance().getSystemService(ALARM_SERVICE);
        am.cancel(getOperation());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "on create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "on destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : "";
        Log.d(TAG, String.format("on start command [%s]", action));

        if (ACTION_GET.equals(action)) {
            resetTheTime();
            onActionGet();
        } else if (ACTION_POST.equals(action)) {
            if (intent != null) {
                String data = intent.getStringExtra("data");
                if (data != null) {
                    onActionPost(data);
                }
            }
        }
        else if (ACTION_UPDATE.equals(action)) {
            resetTheTime();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    protected void onActionGet() {
    }

    protected void onActionPost(String data) {
    }

    private void resetTheTime() {
        Log.d(TAG, "reset the time");

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, getUpdateInterval());

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getOperation());
    }

    protected int getUpdateInterval() {
        return 10;
    }

    protected static PendingIntent getOperation() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction(ACTION_GET);
        PendingIntent sender = PendingIntent.getService(GlobalContext.getInstance().getBaseContext()
                , 1000, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return sender;
    }
}
