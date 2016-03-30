package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

public class Order extends TableRecord {
    private static TableSchema.Column[] columns = TableSchema.OrderEntry.COLUMNS;
    public static String STATUS_NEW = "new";
    public static String STATUS_REJECTED = "rejected";
    public static String STATUS_ACCEPTED = "accepted";
    public static String STATUS_PREPARE = "prepare";
    public static String STATUS_CHECK_LOADING = "check_loading";
    public static String STATUS_LOADED = "loaded";
    public static String STATUS_PAID = "paid";
    public static String STATUS_CHECK_RETURN = "check_return";
    public static String STATUS_RETURNED = "returned";
    public static String STATUS_CLAIMED = "claimed";
    public static String STATUS_SETTLED = "settled";
    public static String STATUS_NONE = "";
}
