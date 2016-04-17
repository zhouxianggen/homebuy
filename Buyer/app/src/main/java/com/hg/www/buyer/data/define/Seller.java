package com.hg.www.buyer.data.define;

import com.hg.www.buyer.data.db.TableSchema;

public class Seller extends TableRecord {
    {
        TAG = Commodity.class.getSimpleName();
        columns = TableSchema.SellerEntry.COLUMNS;
    }
}
