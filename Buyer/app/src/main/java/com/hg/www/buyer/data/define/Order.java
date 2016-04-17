package com.hg.www.buyer.data.define;

import com.hg.www.buyer.data.db.TableSchema;

public class Order extends TableRecord {
    {
        TAG = Order.class.getSimpleName();
        columns = TableSchema.OrderEntry.COLUMNS;
    }
    public static String STATUS_NEW = "new";
    public static String STATUS_REJECTED = "rejected";
    public static String STATUS_ACCEPTED = "accepted";
    public static String STATUS_PREPARE = "prepare";
    public static String STATUS_LOADED = "loaded";
    public static String STATUS_PAID = "paid";
    public static String STATUS_RETURNED = "returned";
    public static String STATUS_CLAIMED = "claimed";
    public static String STATUS_NONE = "";
}
