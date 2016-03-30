package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

public class Message extends TableRecord {
    private static TableSchema.Column[] columns = TableSchema.MessageEntry.COLUMNS;
    public static String STATUS_NEW = "new";
    public static String STATUS_CHECKED = "checked";
    public static String PREPARE_ORDER = "PREPARE_ORDER";
    public static String CHECK_LOADING_ORDER = "CHECK_LOADING_ORDER";
    public static String CHECK_RETURN_ORDER = "CHECK_RETURN_ORDER";

    public boolean parseFromString(String s) {
        return false;
    }
}
