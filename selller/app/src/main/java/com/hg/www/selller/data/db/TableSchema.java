package com.hg.www.selller.data.db;

import android.provider.BaseColumns;

public final class TableSchema {
    public TableSchema() {}

    public static abstract class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "t_order";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_AGENCY_ID = "agency_id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_COMMODITY_ID = "commodity_id";
        public static final String COLUMN_NAME_EXPRESSMAN_ID = "expressman_id";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_PAYMENT = "payment";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_LOADING_TIMESTAMP = "loading_timestamp";
        public static final String COLUMN_NAME_RETURN_TIMESTAMP = "return_timestamp";
        public static final String COLUMN_NAME_PAY_TIMESTAMP = "pay_timestamp";
        public static final String COLUMN_NAME_SETTLE_TIMESTAMP = "settle_timestamp";
    }

    public static abstract class BarcodeEntry implements BaseColumns {
        public static final String TABLE_NAME = "barcode";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_COMMODITY_TITLE = "commodity_title";
        public static final String COLUMN_NAME_COMMODITY_PRICE = "commodity_price";
        public static final String COLUMN_NAME_COMMODITY_UNIT = "commodity_unit";
    }

    public static abstract class CommodityEntry implements BaseColumns {
        public static final String TABLE_NAME = "commodity";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_IMAGE_1 = "image_1";
        public static final String COLUMN_NAME_IMAGE_2 = "image_2";
        public static final String COLUMN_NAME_IMAGE_3 = "image_3";
        public static final String COLUMN_NAME_IMAGE_4 = "image_4";
        public static final String COLUMN_NAME_IMAGE_5 = "image_5";
        public static final String COLUMN_NAME_IMAGE_6 = "image_6";
        public static final String COLUMN_NAME_IMAGE_7 = "image_7";
        public static final String COLUMN_NAME_IMAGE_8 = "image_8";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SUPPORT_RETURN = "support_return";
        public static final String COLUMN_NAME_IN_DISCOUNT = "in_discount";
        public static final String COLUMN_NAME_IN_STOCK = "in_stock";
        public static final String COLUMN_NAME_WEEKLY_SALES = "weekly_sales";
        public static final String COLUMN_NAME_MONTHLY_SALES = "monthly_sales";
        public static final String COLUMN_NAME_WEEKLY_RETURNS = "weekly_returns";
        public static final String COLUMN_NAME_MONTHLY_RETURNS = "monthly_returns";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

    public static abstract class CommodityCategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "commodity_category";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ITEM_COUNT = "item_count";
        public static final String COLUMN_NAME_PARENT = "parent";
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
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_EXPRESSMAN_ID = "expressman_id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_STATUS = "status";
    }
}
