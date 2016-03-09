package com.hg.www.selller.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.define.ExpressmanMessage;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "seller.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ORDER_TABLE =
            "CREATE TABLE " + TableSchema.OrderEntry.TABLE_NAME + " (" +
                    TableSchema.OrderEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_PAYMENT + REAL_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_LOADING_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_RETURN_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_PAY_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_SETTLE_TIMESTAMP + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.OrderEntry.TABLE_NAME;

    private static final String SQL_CREATE_COMMODITY_TABLE =
            "CREATE TABLE " + TableSchema.CommodityEntry.TABLE_NAME + " (" +
                    TableSchema.CommodityEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_2 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_3 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_4 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_5 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_6 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_7 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_8 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_SALES + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_RETURNS + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_MONTHLY_RETURNS + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_COMMODITY_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.CommodityEntry.TABLE_NAME;

    private static final String SQL_CREATE_COMMODITY_CATEGORY_TABLE =
            "CREATE TABLE " + TableSchema.CommodityCategoryEntry.TABLE_NAME + " (" +
                    TableSchema.CommodityCategoryEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    TableSchema.CommodityCategoryEntry.COLUMN_NAME_SELLER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityCategoryEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityCategoryEntry.COLUMN_NAME_ITEM_COUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityCategoryEntry.COLUMN_NAME_PARENT + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_COMMODITY_CATEGORY_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.CommodityCategoryEntry.TABLE_NAME;

    private static final String SQL_CREATE_EXPRESSMAN_TABLE =
            "CREATE TABLE " + TableSchema.ExpressmanEntry.TABLE_NAME + " (" +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanEntry.COLUMN_NAME_ICON + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_EXPRESSMAN_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.ExpressmanEntry.TABLE_NAME;

    private static final String SQL_CREATE_EXPRESSMAN_MESSAGE_TABLE =
            "CREATE TABLE " + TableSchema.ExpressmanMessageEntry.TABLE_NAME + " (" +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    TableSchema.ExpressmanMessageEntry.COLUMN_NAME_STATUS + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_EXPRESSMAN_MESSAGE_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.ExpressmanMessageEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COMMODITY_TABLE);
        db.execSQL(SQL_CREATE_COMMODITY_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
        db.execSQL(SQL_CREATE_EXPRESSMAN_TABLE);
        db.execSQL(SQL_CREATE_EXPRESSMAN_MESSAGE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_COMMODITY_TABLE);
        db.execSQL(SQL_DELETE_COMMODITY_CATEGORY_TABLE);
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
        mDbHelper.onUpgrade(db, 0, 0);

        ContentValues values = new ContentValues();
        values.put(TableSchema.OrderEntry.COLUMN_NAME_ID, "order_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID, "agency_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID, "commodity_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID, "expressman_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT, 1);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT, 3.5);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_STATUS, "new");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_LOADING_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_RETURN_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_PAY_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_SETTLE_TIMESTAMP, "1");
        db.insert(TableSchema.OrderEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.OrderEntry.COLUMN_NAME_ID, "order_2");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID, "agency_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID, "commodity_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID, "expressman_1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_AMOUNT, 2);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT, 7.0);
        values.put(TableSchema.OrderEntry.COLUMN_NAME_STATUS, "new");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_LOADING_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_RETURN_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_PAY_TIMESTAMP, "1");
        values.put(TableSchema.OrderEntry.COLUMN_NAME_SETTLE_TIMESTAMP, "1");
        db.insert(TableSchema.OrderEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_ID, "commodity_1");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_TITLE, "立白透明皂");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL, "http://www.zyzjgww.com/images/201501/goods_img/897_P_1422469213669.jpg");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1, "http://www.bl-cs.com/pic/20158248536.jpg");
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_PRICE, 3.5);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN, 1);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT, 0);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, 1);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES, 100);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_RETURNS, 4);
        values.put(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY, "commodity_category_1");
        db.insert(TableSchema.CommodityEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ID, "commodity_category_1");
        values.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_SELLER_ID, "seller_1");
        values.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_TITLE, "洗涤用品");
        values.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_ITEM_COUNT, 1);
        values.put(TableSchema.CommodityCategoryEntry.COLUMN_NAME_PARENT, "root");
        db.insert(TableSchema.CommodityCategoryEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_ID, "expressman_1");
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_NAME, "老李");
        values.put(TableSchema.ExpressmanEntry.COLUMN_NAME_ICON, "http://img.cool80.com/i/png/397/04.png");
        db.insert(TableSchema.ExpressmanEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_ID, "expressman_message_1");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TYPE, ExpressmanMessage.PREPARE_ORDER);
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_TIMESTAMP, "2016-02-20 09:58");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_EXPRESSMAN_ID, "expressman_1");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_CONTENT, "请准备好订单货物");
        values.put(TableSchema.ExpressmanMessageEntry.COLUMN_NAME_STATUS, ExpressmanMessage.STATUS_NEW);
        db.insert(TableSchema.ExpressmanMessageEntry.TABLE_NAME, null, values);
    }
}
