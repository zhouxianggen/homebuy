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

public class ExpressmanMessageApi {
    private static ExpressmanMessageApi instance = null;
    private DbHelper mDbHelper = null;

    private ExpressmanMessageApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static ExpressmanMessageApi getInstance() {
        if (instance == null) {
            instance = new ExpressmanMessageApi();
        }
        return instance;
    }

    public List<ExpressmanMessage> getMessages() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] columns = {
                TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID,
                TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE,
                TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIME,
                TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID,
                TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT
        };
        Cursor c = db.query(
                TableSchema.ExpressmanMessageEntry.TABLE_NAME,
                columns, null, null, null, null, null
        );

        List<ExpressmanMessage> messages = new ArrayList<>();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ExpressmanMessage message = new ExpressmanMessage();
            message.id = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID));
            message.type = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE));
            message.time = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIME));
            message.content = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT));
            String expressman_id = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID));
            Expressman expressman = ExpressmanApi.getInstance().getExpressman(expressman_id);
            if (expressman == null) {
                c.moveToNext();
                continue;
            }
            message.expressman = expressman;
            messages.add(message);
            c.moveToNext();
        }

        return messages;
    }
}
