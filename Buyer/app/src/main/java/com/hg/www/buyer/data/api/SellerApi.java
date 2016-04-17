package com.hg.www.buyer.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.data.db.DbHelper;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.PullOption;
import com.hg.www.buyer.data.define.Seller;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SellerApi {
    public static final String TAG = SellerApi.class.getSimpleName();
    private static SellerApi instance = null;
    private DbHelper mDbHelper = null;

    private SellerApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static SellerApi getInstance() {
        if (instance == null) {
            instance = new SellerApi();
        }
        return instance;
    }

    public List<Seller> getSellers() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s",
                        TableSchema.SellerEntry.TABLE_NAME),
                null
        );

        List<Seller> sellers = new ArrayList<>();
        while (c.moveToNext()) {
            Seller seller = new Seller();
            if (seller.parseFromCursor(c)) {
                sellers.add(seller);
            }
        }
        return sellers;
    }

    public boolean sync(JSONObject seller) {
        try {
            int id = seller.getInt(TableSchema.SellerEntry.COLUMN_NAME_ID);
            String status = seller.getString(TableSchema.CommodityEntry.COLUMN_NAME_STATUS);
            if (status.equals(Seller.STATUS_DELETED)) {
                Log.d(TAG, String.format("delete seller [%d]", id));
                return deleteSeller(id);
            } else {
                Log.d(TAG, String.format("replace seller [%d]", id));
                return insertSeller(seller);
            }
        } catch (org.json.JSONException e) {
            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
            return false;
        }
    }

    public boolean deleteSeller(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.SellerEntry.TABLE_NAME,
                String.format("%s = %d",
                        TableSchema.SellerEntry.COLUMN_NAME_ID,
                        id),
                null) > 0;
    }

    public boolean insertSeller(JSONObject object) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();

        try {
            for (TableSchema.Column column : TableSchema.SellerEntry.COLUMNS) {
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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT %s,%s from %s",
                        TableSchema.SellerEntry.COLUMN_NAME_ID,
                        TableSchema.SellerEntry.COLUMN_NAME_MODIFY_TIME,
                        TableSchema.SellerEntry.TABLE_NAME),
                null
        );
        PullOption option = new PullOption();
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(TableSchema.SellerEntry.COLUMN_NAME_ID));
            int time = c.getInt(c.getColumnIndexOrThrow(TableSchema.SellerEntry.COLUMN_NAME_MODIFY_TIME));
            if (time > option.lastModifyTime) {
                option.lastModifyTime = time;
            }
            option.check += id;
        }
        Log.d(TAG, String.format("get last modify time [%d], check [%d]", option.lastModifyTime, option.check));
        return option;
    }
}
