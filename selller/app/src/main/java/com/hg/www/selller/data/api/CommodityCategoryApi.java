package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.CommodityCategory;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.data.define.OrderPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityCategoryApi {
    private static CommodityCategoryApi instance = null;
    private DbHelper mDbHelper = null;

    private CommodityCategoryApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static CommodityCategoryApi getInstance() {
        if (instance == null) {
            instance = new CommodityCategoryApi();
        }
        return instance;
    }

    public CommodityCategory createCategory() {
        CommodityCategory category = new CommodityCategory();
        category.seller_id = GlobalContext.getInstance().getSellerId();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        category.id = category.seller_id + ts;
        return category;
    }

    public boolean insertCategory(CommodityCategory category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ID, category.id);
        insertValues.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_SELLER_ID, category.seller_id);
        insertValues.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_TITLE, category.title);
        insertValues.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ITEM_COUNT, category.item_count);
        insertValues.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_PARENT, category.parent);

        db.insert(TableSchema.CommodityCategoryEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public boolean deleteCategory(CommodityCategory category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CommodityCategoryEntry.TABLE_NAME,
                TableSchema.CommodityCategoryEntry.COLUMN_NAME_ID + "=?",
                new String[] {category.id}) > 0;
    }

    public List<CommodityCategory> getCategories(String parent) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from commodity_category WHERE parent=?",
                new String[]{parent}
        );

        List<CommodityCategory> categories = new ArrayList<>();
        while (c.moveToNext()) {
            CommodityCategory category = new CommodityCategory();
            category.id = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ID));
            category.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityCategoryEntry.COLUMN_NAME_SELLER_ID));
            category.title = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityCategoryEntry.COLUMN_NAME_TITLE));
            category.item_count = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ITEM_COUNT));
            category.parent = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityCategoryEntry.COLUMN_NAME_PARENT));
            categories.add(category);
        }

        return categories;
    }
}
