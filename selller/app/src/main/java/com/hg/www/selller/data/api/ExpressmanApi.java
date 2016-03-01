package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Expressman;
import com.hg.www.selller.data.define.ExpressmanMessage;

import java.util.ArrayList;
import java.util.List;

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

    public Expressman getExpressman(String expressman_id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM ? WHERE id = ?",
                new String[]{TableSchema.ExpressmanEntry.TABLE_NAME, expressman_id}
        );

        List<ExpressmanMessage> messages = new ArrayList<>();
        if (c.moveToNext()) {
            Expressman expressman = new Expressman();
            expressman.id = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanEntry.COLUMN_NAME_ID));
            expressman.name = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanEntry.COLUMN_NAME_NAME));
            expressman.icon = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanEntry.COLUMN_NAME_ICON));
            return expressman;
        }
        return null;
    }
}
