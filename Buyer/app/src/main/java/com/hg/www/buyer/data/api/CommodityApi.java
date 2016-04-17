package com.hg.www.buyer.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.R;
import com.hg.www.buyer.data.db.DbHelper;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Category;
import com.hg.www.buyer.data.define.Commodity;
import com.hg.www.buyer.data.define.PullOption;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommodityApi {
    public static final String TAG = CommodityApi.class.getSimpleName();
    private static CommodityApi instance = null;
    private DbHelper mDbHelper = null;

    private CommodityApi() {
        mDbHelper = new DbHelper(GlobalContext.getInstance());
    }

    public static CommodityApi getInstance() {
        if (instance == null) {
            instance = new CommodityApi();
        }
        return instance;
    }

    public List<Commodity> getCommodities(int category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID,
                        category),
                null
        );

        List<Commodity> commodities = new ArrayList<>();
        while (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                commodities.add(commodity);
            }
        }

        Log.d(TAG, String.format("get [%d] commodities for parent [%d]", commodities.size(), category));
        return commodities;
    }

    public List<Commodity> getSellerCategoryCommodities(int sellerId, String category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d AND %s = '%s' ORDER BY weekly_sales DESC",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID, sellerId,
                        TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY, category),
                null
        );

        List<Commodity> commodities = new ArrayList<>();
        while (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                commodities.add(commodity);
            }
        }

        Log.d(TAG, String.format("get [%d] commodities for seller [%d], category [%s]",
                commodities.size(), sellerId, category));
        return commodities;
    }

    private class CategorySales implements Comparable<CategorySales>{
        public String category;
        public int sales;

        public CategorySales(String category, int sales) {
            this.category = category;
            this.sales = sales;
        }

        public int compareTo(CategorySales object) {
            return object.sales - this.sales;
        }
    }

    public List<String> getSellerCategories(int sellerId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID,
                        sellerId),
                null
        );

        int maxSales = 0;
        boolean hasInDiscount = false;
        HashMap<String, Integer> categorySales = new HashMap<>();

        while (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (!commodity.parseFromCursor(c)) {
                continue;
            }
            String category = commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY);
            int sales = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES);
            int inDiscount = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT);
            if (sales > 0) {
                maxSales = Math.max(sales, maxSales);
            }
            if (inDiscount != 0) {
                hasInDiscount = true;
            }
            if (!categorySales.containsKey(category)) {
                categorySales.put(category, sales);
            } else {
                categorySales.put(category, categorySales.get(category) + sales);
            }
        }

        List<CategorySales> tmpList = new ArrayList<>();
        Iterator it = categorySales.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            tmpList.add(new CategorySales((String)pair.getKey(), (int)pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
        Collections.sort(tmpList);

        List<String> categories = new ArrayList<>();
        if (maxSales > 10) {
            categories.add(GlobalContext.getInstance().getString(R.string.category_in_hot));
        }
        if (hasInDiscount) {
            categories.add(GlobalContext.getInstance().getString(R.string.category_in_discount));
        }

        for (CategorySales cs : tmpList) {
            categories.add(cs.category);
        }

        Log.d(TAG, String.format("get [%d] categories", categories.size()));
        return categories;
    }

    public Commodity getCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT * from %s WHERE %s = %d",
                        TableSchema.CommodityEntry.TABLE_NAME,
                        TableSchema.CommodityEntry.COLUMN_NAME_ID,
                        id),
                null
        );

        if (c.moveToNext()) {
            Commodity commodity = new Commodity();
            if (commodity.parseFromCursor(c)) {
                return commodity;
            }
        }

        return null;
    }

    public boolean sync(JSONObject commodity) {
        try {
            int id = commodity.getInt(TableSchema.CommodityEntry.COLUMN_NAME_ID);
            String status = commodity.getString(TableSchema.CommodityEntry.COLUMN_NAME_STATUS);
            if (status.equals(Commodity.STATUS_DELETED)) {
                Log.d(TAG, String.format("delete commodity [%d]", id));
                return deleteCommodity(id);
            } else {
                Log.d(TAG, String.format("replace commodity [%d]", id));
                return insertCommodity(commodity);
            }
        } catch (org.json.JSONException e) {
            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
            return false;
        }
    }

    public boolean deleteCommodity(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CommodityEntry.TABLE_NAME,
                String.format("%s = %d",
                        TableSchema.CommodityEntry.COLUMN_NAME_ID,
                        id),
                null) > 0;
    }

    public boolean insertCommodity(JSONObject object) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();

        try {
            for (TableSchema.Column column : TableSchema.CommodityEntry.COLUMNS) {
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

        db.replace(TableSchema.CommodityEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public PullOption getPullOption(int sellerId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                String.format("SELECT %s,%s from %s WHERE seller_id=%d",
                        TableSchema.CommodityEntry.COLUMN_NAME_ID,
                        TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME,
                        TableSchema.CommodityEntry.TABLE_NAME,
                        sellerId),
                null
        );
        PullOption option = new PullOption();
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_ID));
            int time = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME));
            if (time > option.lastModifyTime) {
                option.lastModifyTime = time;
            }
            option.check += id;
        }
        Log.d(TAG, String.format("get last modify time [%d], check [%d]", option.lastModifyTime, option.check));
        return option;
    }

}
