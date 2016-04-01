package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommodityApi {
    public static final String TAG = CommodityApi.class.getSimpleName();
    private static CommodityApi instance = null;
    private DbHelper mDbHelper = null;

    private CommodityApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static CommodityApi getInstance() {
        if (instance == null) {
            instance = new CommodityApi();
        }
        return instance;
    }

    public Commodity createCommodity() {
        Commodity commodity = new Commodity();
        commodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID,
                GlobalContext.getInstance().getSellerId());
        return commodity;
    }

    public boolean deleteCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CommodityEntry.TABLE_NAME,
                String.format("%s = %d",
                        TableSchema.CommodityEntry.COLUMN_NAME_ID,
                        id),
                null) > 0;
    }

    public boolean insertCommodity(JSONObject object) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();

        try {
            for (TableSchema.Column column : TableSchema.CommodityEntry.COLUMNS) {
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

        db.replace(TableSchema.CommodityEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public List<Commodity> getCommodities(int category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID,
                        category),
                null
        );

        List<Commodity> commodities = new ArrayList<>();
        while (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                commodities.add(commodity);
            }
        }

        Log.d(TAG, String.format("get [%d] commodities for parent [%d]", commodities.size(), category));
        return commodities;
    }

    public Commodity getCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_ID,
                        id),
                null
        );

        if (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                return commodity;
            }
        }

        return null;
    }

    public Commodity getCommodityByBarcode(String barcode) {
        if (barcode == null) {
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %s",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_BARCODE,
                        barcode),
                null
        );

        if (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                return commodity;
            }
        }

        return null;
    }

    public int getLatestModifyTime() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT %s from %s ORDER BY %s DESC LIMIT 1",
                        TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME,
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME),
                null
        );
        int t = 0;
        if (c.moveToNext()) {
            t = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME));
        }
        Log.d(TAG, String.format("get last modify time [%d]", t));
        return t;
    }

}
