package com.hg.www.selller.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hg.www.selller.GlobalContext;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "seller.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ORDER_TABLE =
            "CREATE TABLE " + TableSchema.OrderEntry.TABLE_NAME + " (" +
                    TableSchema.OrderEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_PAYMENT + REAL_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_STATUS + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.OrderEntry.TABLE_NAME;

    private static final String SQL_CREATE_COMMODITY_TABLE =
            "CREATE TABLE " + TableSchema.CommodityEntry.TABLE_NAME + " (" +
                    TableSchema.CommodityEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGES + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_SALE_VOLUME + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_RETURN_VOLUME + INTEGER_TYPE +
                    " )";
    private static final String SQL_DELETE_COMMODITY_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.CommodityEntry.TABLE_NAME;

    private static final String SQL_CREATE_EXPRESSMAN_TABLE =
            "CREATE TABLE " + TableSchema.ExpressmanEntry.TABLE_NAME + " (" +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_ICON + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_EXPRESSMAN_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.ExpressmanEntry.TABLE_NAME;

    private static final String SQL_CREATE_EXPRESSMAN_MESSAGE_TABLE =
            "CREATE TABLE " + TableSchema.ExpressmanMessageEntry.TABLE_NAME + " (" +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_EXPRESSMAN_MESSAGE_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.ExpressmanMessageEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COMMODITY_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
        db.execSQL(SQL_CREATE_EXPRESSMAN_TABLE);
        db.execSQL(SQL_CREATE_EXPRESSMAN_MESSAGE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_COMMODITY_TABLE);
        db.execSQL(SQL_DELETE_ORDER_TABLE);
        db.execSQL(SQL_DELETE_EXPRESSMAN_TABLE);
        db.execSQL(SQL_DELETE_EXPRESSMAN_MESSAGE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    static public void fackData() {
        DbHelper mDbHelper = new DbHelper(GlobalContext.getInstance());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableSchema.OrderEntry.COLUMN_NAME_ID, "order_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID, "agency_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID, "commodity_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID, "expressman_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT, 1);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT, 3.5);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_STATUS, "new");
        db.insert(TableSchema.OrderEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_ID, "order_1");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_TITLE, "立白透明皂");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGES, "http://www.zyzjgww.com/images/201501/goods_img/897_P_1422469213669.jpg|http://www.bl-cs.com/pic/20158248536.jpg");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_PRICE, 3.5);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN, 1);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT, 0);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, 1);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_SALE_VOLUME, 100);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_RETURN_VOLUME, 4);
        db.insert(TableSchema.CommodityEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_ID, "expressman_1");
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_NAME, "老李");
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_ICON, "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjzzMTVz5_LAhUBFqYKHXdyBlkQjRwIBw&url=http%3A%2F%2Fmccormickld.deviantart.com%2Fart%2FPng-Obama-Smoke-Xlarge-438633148&psig=AFQjCNELgExxn3LJZTtdHXMHFLr-8sZBmw&ust=1456926310609045");
        db.insert(TableSchema.ExpressmanEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID, "expressman_1");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE, "PREPARE");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIME, "2016-02-20 09:58");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID, "expressman_1");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT, "请准备好订单货物");
        db.insert(TableSchema.ExpressmanMessageEntry.TABLE_NAME, null, values);
    }
}
