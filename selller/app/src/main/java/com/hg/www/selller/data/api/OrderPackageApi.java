package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.data.define.OrderPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPackageApi {
    private static final String TAG = OrderPackageApi.class.getSimpleName();
    private static OrderPackageApi instance = null;
    private DbHelper mDbHelper = null;

    private OrderPackageApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static OrderPackageApi getInstance() {
        if (instance == null) {
            instance = new OrderPackageApi();
        }
        return instance;
    }

    public List<OrderPackage> getPackages() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from t_order",
                null
        );

        HashMap<String, List<Order>> statusOrders = new HashMap<>();
        while (c.moveToNext()) {
            Order order = OrderApi.CreateFromCursor(c);
            if (statusOrders.get(order.status) == null) {
                statusOrders.put(order.status, new ArrayList<Order>());
            }
            statusOrders.get(order.status).add(order);
        }

        String[] statuses = new String[]{Order.STATUS_NEW, Order.STATUS_ACCEPTED,
            Order.STATUS_LOADED, Order.STATUS_PAID, Order.STATUS_RETURNED, Order.STATUS_CLAIMED};
        List<OrderPackage> orderPackages = new ArrayList<>();
        for (String status : statuses) {
            OrderPackage orderPackage = new OrderPackage();
            orderPackage.status = status;
            orderPackage.orders = statusOrders.get(status);
            orderPackages.add(orderPackage);
            if (orderPackage.orders == null) {
                orderPackage.orders = new ArrayList<>();
            }
            Log.d(TAG, "status " + status + " have " + String.valueOf(orderPackage.orders.size()));
        }

        return orderPackages;
    }
}
