package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Barcode;

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

    public Barcode getBarcode(String code) {
        if (code == null) {
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ? ",
                        TableSchema.BarcodeEntry.TABLE_NAME,
                        TableSchema.BarcodeEntry.COLUMN_NAME_CODE),
                new String[]{code}
        );

        if (c.moveToNext()) {
            Barcode barcode = new Barcode();
            if (barcode.parseFromCursor(c)) {
                return barcode;
            }
        }

        return null;
    }
}
