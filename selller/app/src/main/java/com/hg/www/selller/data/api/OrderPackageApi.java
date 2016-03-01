package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                "SELECT * from ?",
                new String[]{TableSchema.OrderEntry.TABLE_NAME}
        );

        HashMap<String, List<Order>> orders = new HashMap<>();
        while (c.moveToNext()) {
            Order order = new Order();
            order.id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            order.agency_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID));
            order.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID));
            order.commodity_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID));
            order.expressman_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID));
            order.amount = c.getInt(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT));
            order.payment = c.getFloat(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            order.status = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            if (orders.get(order.status) == null) {
                orders.put(order.status, new ArrayList<Order>());
            }
            orders.get(order.status).add(order);
        }

        List<OrderPackage> orderPackages = new ArrayList<>();
        for (Map.Entry<String, List<Order>> e : orders.entrySet()) {
            OrderPackage orderPackage = new OrderPackage();
            orderPackage.status = e.getKey();
            orderPackage.orders = e.getValue();
            orderPackages.add(orderPackage);
        }

        return orderPackages;
    }
}
