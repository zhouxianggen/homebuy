package com.hg.www.selller.service;

import android.content.Context;
import android.content.Intent;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.define.ExpressmanMessage;
import com.hg.www.selller.data.define.Order;

import java.util.List;

public class ExpressmanMessageService extends BasicService {
    public static final String TAG = ExpressmanMessageService.class.getSimpleName();

    public static void startService() {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        Intent intent = new Intent(GlobalContext.getInstance(), OrderService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    private int getUpdateInterval() {
        return AppSettings.getUpdateExpressmanMessageInterval();
    }

    private void onActionGet() {
    }

    static public void updateServerMessage(
            Context context,
            ExpressmanMessage message,
            int timeout,
            HttpAsyncTask.OnSuccessListener onSuccessListener,
            HttpAsyncTask.OnFailureListener onFailureListener) {
        new HttpAsyncTask(context, "", "", "", onSuccessListener, onFailureListener).execute();
    }
}
