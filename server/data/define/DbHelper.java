package com.hg.www.buyer.data.db

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "seller.db";
    private static final String PRIMARY = " PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    private static final String SQL_CREATE_BUYER_TABLE =
            "CREATE TABLE " + TableSchema.BuyerEntry.TABLE_NAME + " (" +
                    TableSchema.BuyerEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_ADDRESS_1 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_ADDRESS_2 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_ADDRESS_3 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.BuyerEntry.COLUMN_NAME_ADDRESS_4 + TEXT_TYPE + COMMA_SEP +
                    PRIMARY + " (" +
                        TableSchema.BuyerEntry.COLUMN_NAME_ID + ") " + 
            " )";
    private static final String SQL_DELETE_BUYER_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.BuyerEntry.TABLE_NAME;

    private static final String SQL_CREATE_ORDER_TABLE =
            "CREATE TABLE " + TableSchema.OrderEntry.TABLE_NAME + " (" +
                    TableSchema.OrderEntry.COLUMN_NAME_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_BUYER_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_AGENCY_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_SELLER_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_EXPRESSMAN_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_PAYMENT + REAL_TYPE + COMMA_SEP +
                    TableSchema.OrderEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    PRIMARY + " (" +
                        TableSchema.OrderEntry.COLUMN_NAME_ID + ") " + 
            " )";
    private static final String SQL_DELETE_ORDER_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.OrderEntry.TABLE_NAME;

    private static final String SQL_CREATE_SELLER_TABLE =
            "CREATE TABLE " + TableSchema.SellerEntry.TABLE_NAME + " (" +
                    TableSchema.SellerEntry.COLUMN_NAME_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_PSWD + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_IMAGE_1 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_IMAGE_2 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_IMAGE_3 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_IMAGE_4 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_PAYMENT_ID + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_PAYMENT_BANK + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_PAYMENT_CARD + TEXT_TYPE + COMMA_SEP +
                    TableSchema.SellerEntry.COLUMN_NAME_MODIFY_TIME + INTEGER_TYPE + COMMA_SEP +
                    PRIMARY + " (" +
                        TableSchema.SellerEntry.COLUMN_NAME_ID + ") " + 
            " )";
    private static final String SQL_DELETE_SELLER_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.SellerEntry.TABLE_NAME;

    private static final String SQL_CREATE_COMMODITY_TABLE =
            "CREATE TABLE " + TableSchema.CommodityEntry.TABLE_NAME + " (" +
                    TableSchema.CommodityEntry.COLUMN_NAME_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_SELLER_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_2 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_3 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_4 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_5 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_6 + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_SALES + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_WEEKLY_RETURNS + INTEGER_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    TableSchema.CommodityEntry.COLUMN_NAME_MODIFY_TIME + INTEGER_TYPE + COMMA_SEP +
                    PRIMARY + " (" +
                        TableSchema.CommodityEntry.COLUMN_NAME_ID + ") " + 
            " )";
    private static final String SQL_DELETE_COMMODITY_TABLE =
            "DROP TABLE IF EXISTS " + TableSchema.CommodityEntry.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BUYER_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
        db.execSQL(SQL_CREATE_SELLER_TABLE);
        db.execSQL(SQL_CREATE_COMMODITY_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BUYER_TABLE);
        db.execSQL(SQL_DELETE_ORDER_TABLE);
        db.execSQL(SQL_DELETE_SELLER_TABLE);
        db.execSQL(SQL_DELETE_COMMODITY_TABLE);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
