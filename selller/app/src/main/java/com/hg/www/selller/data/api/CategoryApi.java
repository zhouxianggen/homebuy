package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Category;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryApi {
    private static CategoryApi instance = null;
    private DbHelper mDbHelper = null;

    private CategoryApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static CategoryApi getInstance() {
        if (instance == null) {
            instance = new CategoryApi();
        }
        return instance;
    }

    public Category createCategory() {
        Category category = new Category();
        category.setIntProperty(TableSchema.CategoryEntry.COLUMN_NAME_SELLER_ID, GlobalContext.getInstance().getSellerId());
        return category;
    }

    public boolean insertCategory(JSONObject object) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();

        try {
            for (TableSchema.Column column : TableSchema.CategoryEntry.COLUMNS) {
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

        db.replace(TableSchema.CategoryEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public boolean deleteCategory(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CategoryEntry.TABLE_NAME,
                String.format("%s = ?", TableSchema.CategoryEntry.COLUMN_NAME_ID),
                new String[] {String.valueOf(id)}) > 0;
    }

    public List<Category> getCategories(int parent) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = ?",
                        TableSchema.CategoryEntry.TABLE_NAME, TableSchema.CategoryEntry.COLUMN_NAME_PARENT),
                new String[]{String.valueOf(parent)}
        );

        List<Category> categories = new ArrayList<>();
        while (c.moveToNext()) {
            Category category = new Category();
            if (category.parseFromCursor(c)) {
                categories.add(category);
            }
        }

        return categories;
    }

    public int getLatestModifyTime() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT %s from %s ORDER BY %s DESC LIMIT 1",
                        TableSchema.CategoryEntry.COLUMN_NAME_MODIFY_TIME,
                        TableSchema.CategoryEntry.TABLE_NAME,
                        TableSchema.CategoryEntry.COLUMN_NAME_MODIFY_TIME),
                null
        );
        if (c.moveToNext()) {
            return c.getInt(c.getColumnIndexOrThrow(TableSchema.CategoryEntry.COLUMN_NAME_MODIFY_TIME));
        }
        return 0;
    }
}