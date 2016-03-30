package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageApi {
    private static final String TAG = MessageApi.class.getSimpleName();
    private static MessageApi instance = null;
    private DbHelper mDbHelper = null;

    private MessageApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static MessageApi getInstance() {
        if (instance == null) {
            instance = new MessageApi();
        }
        return instance;
    }

    public Message getMessage(String id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ? ",
                        TableSchema.MessageEntry.TABLE_NAME,
                        TableSchema.MessageEntry.COLUMN_NAME_ID),
                new String[]{String.valueOf(id)}
        );

        if (c.moveToNext()) {
            Message message = new Message();
            if (message.parseFromCursor(c)) {
                return message;
            }
        }

        return null;
    }

    public List<Message> getMessages() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ?",
                        TableSchema.MessageEntry.TABLE_NAME, TableSchema.MessageEntry.COLUMN_NAME_STATUS),
                new String[]{""}
        );

        List<Message> messages = new ArrayList<>();
        while (c.moveToNext()) {
            Message message = new Message();
            if (message.parseFromCursor(c)) {
                messages.add(message);
            }
        }

        return messages;
    }
}
