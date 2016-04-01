package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

public class Barcode extends TableRecord {
    public static final String TAG = Barcode.class.getSimpleName();
    protected TableSchema.Column[] columns = TableSchema.BarcodeEntry.COLUMNS;
}
