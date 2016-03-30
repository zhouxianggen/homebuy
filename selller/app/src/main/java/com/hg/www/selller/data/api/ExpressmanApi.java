package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Expressman;

public class ExpressmanApi {
    private static ExpressmanApi instance = null;
    private DbHelper mDbHelper = null;

    private ExpressmanApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static ExpressmanApi getInstance() {
        if (instance == null) {
            instance = new ExpressmanApi();
        }
        return instance;
    }

    public Expressman getExpressman(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ? ",
                        TableSchema.ExpressmanEntry.TABLE_NAME,
                        TableSchema.ExpressmanEntry.COLUMN_NAME_ID),
                new String[]{String.valueOf(id)}
        );

        if (c.moveToNext()) {
            Expressman expressman = new Expressman();
            if (expressman.parseFromCursor(c)) {
                return expressman;
            }
        }

        return null;
    }
}
