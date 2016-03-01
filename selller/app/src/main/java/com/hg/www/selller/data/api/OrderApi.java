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
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Order order = new Order();
            order.id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            order.agency_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID));
            order.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID));
            order.commodity_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID));
            order.expressman_id = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID));
            order.amount = c.getInt(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT));
            order.payment = c.getFloat(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            order.status = c.getString(c.getColumnIndexOrThrow(TableSchema.OrderEntry.COLUMN_NAME_ID));
            c.moveToNext();
        }

        return orders;
    }

    public boolean pullServerOrders() {
        return true;
    }

    public boolean pushOrders(List<Order> orders) {
        return true;
    }
}
