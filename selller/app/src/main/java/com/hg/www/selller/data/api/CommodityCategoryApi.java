package com.hg.www.selller.data.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
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
