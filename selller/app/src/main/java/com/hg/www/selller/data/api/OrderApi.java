package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderApi {
    private static OrderApi instance = null;
    private DbHelper mDbHelper = null;

    private OrderApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static OrderApi getInstance() {
        if (instance == null) {
            instance = new OrderApi();
        }
        return instance;
    }

    public List<Order> getOrders(String status) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ?",
                        TableSchema.OrderEntry.TABLE_NAME, TableSchema.OrderEntry.COLUMN_NAME_STATUS),
                new String[]{status}
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            Order order = new Order();
            if (order.parseFromCursor(c)) {
                orders.add(order);
            }
        }

        return orders;
    }

    public List<Order> getCheckLoadingOrders(String expressmanId, String checkLoadingTimestamp) {
        List<Order> orders = new ArrayList<>();
        return orders;
    }

    public List<Order> getCheckReturnOrders(String expressmanId, String checkReturnTimestamp) {
        List<Order> orders = new ArrayList<>();
        return orders;
    }

    public List<Order> getPrepareOrders(String messageId, String expressmanId) {
        List<Order> orders = new ArrayList<>();
        return orders;
    }
}
