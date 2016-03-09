package com.hg.www.selller.service;

import android.content.Context;
import android.content.Intent;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.define.Order;

import java.util.List;

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

    static public void updateServerOrders(
            Context context,
            List<Order> orderList,
            int timeout,
            HttpAsyncTask.OnSuccessListener onSuccessListener,
            HttpAsyncTask.OnFailureListener onFailureListener) {
        new HttpAsyncTask(context, "", "", "", onSuccessListener, onFailureListener).execute();
    }
}
