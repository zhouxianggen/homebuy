package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Expressman;
import com.hg.www.selller.data.define.ExpressmanMessage;

import java.util.ArrayList;
import java.util.List;

public class ExpressmanMessageApi {
    private static final String TAG = ExpressmanMessageApi.class.getSimpleName();
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

    public int setMessageStatus(String id, String status) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "UPDATE expressman_message SET status=? WHERE id=?",
                new String[]{status, id}
        );
        return c.getCount();
    }

    public ExpressmanMessage getMessage(String id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * from expressman_message WHERE id=?",
                new String[]{id}
        );

        if (c.moveToNext()) {
            return CreateFromCursor(c);
        }

        return null;
    }

    public List<ExpressmanMessage> getMessages() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * from expressman_message WHERE status=?",
                new String[]{ExpressmanMessage.STATUS_NEW}
        );

        Log.d(TAG, "get rows " + String.valueOf(c.getCount()));
        List<ExpressmanMessage> messages = new ArrayList<>();
        while (c.moveToNext()) {
            messages.add(CreateFromCursor(c));
        }

        return messages;
    }

    public static ExpressmanMessage CreateFromCursor(Cursor c) {
        ExpressmanMessage message = new ExpressmanMessage();
        message.id = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID));
        message.type = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE));
        message.timestamp = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIMESTAMP));
        message.content = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT));
        message.expressman_id = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID));
        message.status = c.getString(c.getColumnIndexOrThrow(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_STATUS));
        return message;
    }
}
