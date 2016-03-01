package com.hg.www.selller.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.CommodityCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BasicService extends Service {
    public static final String TAG = BasicService.class.getSimpleName();
    public static final String ACTION_GET = "com.hg.www.ACTION_GET";
    public static final String ACTION_UPDATE = "com.hg.www.ACTION_UPDATE";

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

        if (ACTION_GET.equals(action)) {
            resetTheTime();
            onActionGet();
        }
        else if (ACTION_UPDATE.equals(action)) {
            resetTheTime();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void onActionGet() {
    }

    private void resetTheTime() {
        Log.d(TAG, "reset the time");

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, getUpdateInterval());

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getOperation());
    }

    private int getUpdateInterval() {
        return 10;
    }

    private static PendingIntent getOperation() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction(ACTION_GET);
        PendingIntent sender = PendingIntent.getService(GlobalContext.getInstance().getBaseContext()
                , 1000, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return sender;
    }
}
