package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

/**
 * 商品类目数据格式.
 */
public class Category extends TableRecord {
    private static TableSchema.Column[] columns = TableSchema.CategoryEntry.COLUMNS;
}
