package com.hg.www.selller.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Order;

import java.util.ArrayList;
import java.util.List;

public class CommodityApi {
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

    public Commodity createCommodity() {
        Commodity commodity = new Commodity();
        commodity.seller_id = GlobalContext.getInstance().getSellerId();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        commodity.id = commodity.seller_id + ts;
        return commodity;
    }

    public boolean deleteCommodity(Commodity commodity) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(TableSchema.CommodityEntry.TABLE_NAME,
                TableSchema.CommodityEntry.COLUMN_NAME_ID + "=?",
                new String[] {commodity.id}) > 0;
    }

    public boolean insertCommodity(Commodity commodity) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_ID, commodity.id);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID, commodity.seller_id);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_BARCODE, commodity.barcode);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_TITLE, commodity.title);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_DESCRIPTION, commodity.description);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL, commodity.thumbnail);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1, commodity.image_1);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_2, commodity.image_2);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_3, commodity.image_3);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_4, commodity.image_4);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_5, commodity.image_5);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_6, commodity.image_6);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_7, commodity.image_7);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_8, commodity.image_8);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_PRICE, commodity.price);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, commodity.in_stock);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT, commodity.in_discount);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN, commodity.support_return);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES, commodity.weekly_sales);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_SALES, commodity.monthly_sales);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_RETURNS, commodity.weekly_returns);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_RETURNS, commodity.monthly_returns);
        insertValues.put(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY, commodity.category);
        db.insert(TableSchema.CommodityEntry.TABLE_NAME, null, insertValues);
        return true;
    }

    public List<Commodity> getCommodities(String category) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from commodity WHERE category=? ",
                new String[]{category}
        );

        List<Commodity> commodities = new ArrayList<>();
        while (c.moveToNext()) {
            commodities.add(CreateFromCursor(c));
        }

        return commodities;
    }

    public Commodity getCommodity(String id) {
        if (id == null) {
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from commodity WHERE id=? ",
                new String[]{id}
        );

        if (c.moveToNext()) {
            return CreateFromCursor(c);
        }

        return null;
    }

    public Commodity getCommodityByBarcode(String barcode) {
        if (barcode == null) {
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * from commodity WHERE barcode=? ",
                new String[]{barcode}
        );

        if (c.moveToNext()) {
            return CreateFromCursor(c);
        }

        return null;
    }

    private Commodity CreateFromCursor(Cursor c) {
        Commodity commodity = new Commodity();
        commodity.id = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_ID));
        commodity.seller_id = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID));
        commodity.title = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_TITLE));
        commodity.thumbnail = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL));
        commodity.image_1 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1));
        commodity.image_2 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_2));
        commodity.image_3 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_3));
        commodity.image_4 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_4));
        commodity.image_5 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_5));
        commodity.image_6 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_6));
        commodity.image_7 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_7));
        commodity.image_8 = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_8));
        commodity.price = c.getFloat(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_PRICE));
        commodity.in_stock = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK));
        commodity.in_discount = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT));
        commodity.support_return = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN));
        commodity.weekly_sales = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES));
        commodity.weekly_returns = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_RETURNS));
        commodity.monthly_sales = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_SALES));
        commodity.monthly_returns = c.getInt(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_RETURNS));
        commodity.category = c.getString(c.getColumnIndexOrThrow(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY));
        return commodity;
    }
}
