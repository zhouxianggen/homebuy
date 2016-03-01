package com.hg.www.selller.data.db;

import android.provider.BaseColumns;

public final class TableSchema {
    public TableSchema() {}

    public static abstract class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "order";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_AGENCY_ID = "agency_id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_COMMODITY_ID = "commodity_id";
        public static final String COLUMN_NAME_EXPRESSMAN_ID = "expressman_id";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_PAYMENT = "payment";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static abstract class CommodityEntry implements BaseColumns {
        public static final String TABLE_NAME = "commodity";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGES = "images";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SUPPORT_RETURN = "support_return";
        public static final String COLUMN_NAME_IN_DISCOUNT = "in_discount";
        public static final String COLUMN_NAME_IN_STOCK = "in_stock";
        public static final String COLUMN_NAME_SALE_VOLUME = "sale_volume";
        public static final String COLUMN_NAME_RETURN_VOLUME = "return_volume";
    }

    public static abstract class ExpressmanEntry implements BaseColumns {
        public static final String TABLE_NAME = "expressman";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ICON = "icon";
    }

    public static abstract class ExpressmanMessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "expressman_message";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_EXPRESSMAN_ID = "expressman_id";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
