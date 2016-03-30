package com.hg.www.selller.data.api;

import android.util.Log;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.data.define.OrderPackage;

import java.util.ArrayList;
import java.util.List;

public class OrderPackageApi {
    private static final String TAG = OrderPackageApi.class.getSimpleName();
    private static OrderPackageApi instance = null;

    private OrderPackageApi() {
    }

    public static OrderPackageApi getInstance() {
        if (instance == null) {
            instance = new OrderPackageApi();
        }
        return instance;
    }

    public List<OrderPackage> getPackages() {
        String[] statuses = new String[]{Order.STATUS_NEW, Order.STATUS_ACCEPTED,
            Order.STATUS_LOADED, Order.STATUS_PAID, Order.STATUS_RETURNED, Order.STATUS_CLAIMED};

        List<OrderPackage> orderPackages = new ArrayList<>();
        for (String status : statuses) {
            OrderPackage orderPackage = new OrderPackage();
            orderPackage.status = status;
            orderPackage.orders = OrderApi.getInstance().getOrders(status);
            orderPackages.add(orderPackage);
            Log.d(TAG, "status " + status + " have " + String.valueOf(orderPackage.orders.size()));
        }

        return orderPackages;
    }
}
