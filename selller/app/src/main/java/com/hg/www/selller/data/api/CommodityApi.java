package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommodityApi {
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
        commodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID, GlobalContext.getInstance().getSellerId());
        return commodity;
    }

    public boolean deleteCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CommodityEntry.TABLE_NAME,
                String.format("%s = ?", TableSchema.CommodityEntry.COLUMN_NAME_ID),
                new String[] {String.valueOf(id)}) > 0;
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
                String.format("SELECT * from %s WHERE %s = ?",
                        TableSchema.CommodityEntry.TABLE_NAME, TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID),
                new String[]{String.valueOf(category)}
        );

        List<Commodity> commodities = new ArrayList<>();
        while (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                commodities.add(commodity);
            }
        }

        return commodities;
    }

    public Commodity getCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ? ",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_ID),
                new String[]{String.valueOf(id)}
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
                String.format("SELECT * from %s WHERE %s = ? ",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_BARCODE),
                new String[]{barcode}
        );

        if (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                return commodity;
            }
        }

        return null;
    }

}
