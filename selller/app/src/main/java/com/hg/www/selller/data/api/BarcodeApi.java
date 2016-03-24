package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Barcode;
import com.hg.www.selller.data.define.CommodityCategory;

import java.util.ArrayList;
import java.util.List;

public class BarcodeApi {
    private static BarcodeApi instance = null;
    private DbHelper mDbHelper = null;

    private BarcodeApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static BarcodeApi getInstance() {
        if (instance == null) {
            instance = new BarcodeApi();
        }
        return instance;
    }

    public Barcode getBarcode(String id) {
        if (id == null) {
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from barcode WHERE id=? ",
                new String[]{id}
        );

        if (c.moveToNext()) {
            return CreateFromCursor(c);
        }

        return null;
    }

    public static Barcode CreateFromCursor(Cursor c) {
        Barcode barcode = new Barcode();
        barcode.id = c.getString(c.getColumnIndexOrThrow(TableSchema.BarcodeEntry.COLUMN_NAME_ID));
        barcode.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.BarcodeEntry.COLUMN_NAME_SELLER_ID));
        barcode.commodity_title = c.getString(c.getColumnIndexOrThrow(TableSchema.BarcodeEntry.COLUMN_NAME_COMMODITY_TITLE));
        barcode.commodity_price = c.getDouble(c.getColumnIndexOrThrow(TableSchema.BarcodeEntry.COLUMN_NAME_COMMODITY_PRICE));
        barcode.commodity_unit = c.getString(c.getColumnIndexOrThrow(TableSchema.BarcodeEntry.COLUMN_NAME_COMMODITY_UNIT));
        return barcode;
    }
}
