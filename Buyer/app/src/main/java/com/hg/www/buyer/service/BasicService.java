package com.hg.www.buyer.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.R;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BasicService extends Service implements ServiceInterface {
    public static final String TAG = BasicService.class.getSimpleName();
    public static final String ACTION_UPDATE = "com.hg.www.ACTION_UPDATE";
    public static final String ACTION_PULL = "com.hg.www.ACTION_PULL";
    public static final String ACTION_PUSH = "com.hg.www.ACTION_PUSH";
    public static final String RESP_STATUS_OK = "ok";
    protected final OkHttpClient client = new OkHttpClient();

    protected static void clearAlarm() {
        AlarmManager am = (AlarmManager) GlobalContext.getInstance().getSystemService(ALARM_SERVICE);
        am.cancel(getOperation());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : "";
        Log.d(TAG, String.format("on start command [%s]", action));

        if (ACTION_UPDATE.equals(action)) {
            String args = intent.getStringExtra(GlobalContext.getInstance().getString(R.string.extra_pull_args));
            new ServiceAsyncTask(null, this).pull(args);
            resetTheTime();
        } else if (ACTION_PULL.equals(action)) {
            String args = intent.getStringExtra(GlobalContext.getInstance().getString(R.string.extra_pull_args));
            new ServiceAsyncTask(null, this).pull(args);
        } else if (ACTION_PUSH.equals(action) && intent != null) {
            String data = intent.getStringExtra(GlobalContext.getInstance().getString(R.string.extra_post_data));
            new ServiceAsyncTask(null, this).push(data);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public String pull(String args) {
        return "";
    }

    public String push(String data) {
        return "";
    }

    public void onFinished(String errors) {}

    protected String get(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return "";
            }

            return response.body().string();
        } catch (IOException e) {
            return "";
        }
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
        intent.setAction(ACTION_UPDATE);
        PendingIntent sender = PendingIntent.getService(GlobalContext.getInstance().getBaseContext()
                , 1000, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return sender;
    }
}
