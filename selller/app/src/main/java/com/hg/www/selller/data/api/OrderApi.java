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

    public List<Order> getLocalOrders() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] columns = {
                TableSchema.OrderEntry.COLUMN_NAME_ID,
                TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID,
                TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID,
                TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID,
                TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID,
                TableSchema.OrderEntry.COLUMN_NAME_AMOUNT,
                TableSchema.OrderEntry.COLUMN_NAME_PAYMENT,
                TableSchema.OrderEntry.COLUMN_NAME_STATUS
        };
        Cursor c = db.query(
                TableSchema.OrderEntry.TABLE_NAME,
                columns, null, null, null, null, null
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            orders.add(CreateFromCursor(c));
        }

        return orders;
    }

    public List<Order> getOrders(String status) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from t_order WHERE status=?",
                new String[]{status}
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            orders.add(CreateFromCursor(c));
        }

        return orders;
    }

    public List<Order> getLoadingOrders(String expressman_id, String loading_timestamp) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from t_order WHERE expressman_id=? AND loading_timestamp=?",
                new String[]{expressman_id, loading_timestamp}
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            orders.add(CreateFromCursor(c));
        }

        return orders;
    }

    public List<Order> getPrepareOrders(String expressman_id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from t_order WHERE expressman_id=? AND status=?",
                new String[]{expressman_id, Order.STATUS_NEW}
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            orders.add(CreateFromCursor(c));
        }

        return orders;
    }

    public List<Order> getReturnOrders(String expressman_id, String timestamp) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from t_order WHERE expressman_id=? AND return_timestamp=?",
                new String[]{expressman_id, timestamp}
        );

        List<Order> orders = new ArrayList<>();
        while (c.moveToNext()) {
            orders.add(CreateFromCursor(c));
        }

        return orders;
    }

    public int setOrderStatus(String id, String status) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "UPDATE t_order SET status=? WHERE id=?",
                new String[]{status, id}
        );
        return c.getCount();
    }

    public String syncOrders(List<Order> orders) {
        return "";
    }

    public static Order CreateFromCursor(Cursor c) {
        Order order = new Order();
        order.id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
        order.agency_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID));
        order.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID));
        order.commodity_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID));
        order.expressman_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID));
        order.amount = c.getInt(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT));
        order.payment = c.getFloat(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT));
        order.status = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_STATUS));
        order.loading_timestamp = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_LOADING_TIMESTAMP));
        order.return_timestamp = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_RETURN_TIMESTAMP));
        return order;
    }

    public boolean pullServerOrders() {
        return true;
    }

    public boolean pushOrders(List<Order> orders) {
        return true;
    }
}
