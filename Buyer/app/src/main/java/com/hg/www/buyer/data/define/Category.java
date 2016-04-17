package com.hg.www.buyer.data.define;

import com.hg.www.buyer.data.db.TableSchema;

public class Category extends TableRecord {
    {
        TAG = Category.class.getSimpleName();
        columns = TableSchema.CategoryEntry.COLUMNS;
    }
}
