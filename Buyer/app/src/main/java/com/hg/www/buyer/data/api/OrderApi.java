package com.hg.www.buyer.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.data.db.DbHelper;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Order;
import com.hg.www.buyer.data.define.PullOption;
import com.hg.www.buyer.data.define.Seller;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderApi {
    public static final String TAG = OrderApi.class.getSimpleName();
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

    public List<Order> getUnfinishedOrders(String buyerId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s=%s",
                        TableSchema.OrderEntry.TABLE_NAME,
                        TableSchema.OrderEntry.COLUMN_NAME_BUYER_ID,
                        buyerId),
                null
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

    public boolean sync(JSONObject order) {
        return insertOrder(order);
    }

    public boolean insertOrder(JSONObject object) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();

        try {
            for (TableSchema.Column column : TableSchema.OrderEntry.COLUMNS) {
                if (column.type == TableSchema.ColumnType.TEXT_TYPE) {
                    insertValues.put(column.name, object.getString(column.name));
                } else if (column.type == TableSchema.ColumnType.INTEGER_TYPE) {
                    insertValues.put(column.name, object.getInt(column.name));
                } else if (column.type == TableSchema.ColumnType.REAL_TYPE) {
                    insertValues.put(column.name, object.getDouble(column.name));
                } else {
                    return false;
                }
            }
        } catch (org.json.JSONException e) {
            return false;
        }

        db.replace(TableSchema.SellerEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public PullOption getPullOption() {
        PullOption option = new PullOption();
        return option;
    }
}
