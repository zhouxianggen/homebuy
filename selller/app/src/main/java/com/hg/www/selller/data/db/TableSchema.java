
package com.hg.www.selller.data.db;

import android.provider.BaseColumns;

public final class TableSchema {
    public TableSchema() {}

    public enum ColumnType {
        TEXT_TYPE,
        INTEGER_TYPE,
        REAL_TYPE
    }

    public static class Column {
        public String name = "";
        public ColumnType type = ColumnType.TEXT_TYPE;

        public Column(String name, ColumnType type) {
            this.name = name;
            this.type = type;
        }
    }

    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "";
        public static final Column[] COLUMNS = new Column[] {};
    }

    public static abstract class AgencyEntry extends Entry {
        public static final String TABLE_NAME = "agency";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PSWD = "pswd";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_TITLE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PSWD, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_ADDRESS, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

    public static abstract class BarcodeEntry extends Entry {
        public static final String TABLE_NAME = "barcode";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_CODE = "code";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
        public static final String COLUMN_NAME_ITEM_SIZE = "item_size";
        public static final String COLUMN_NAME_UNIT_NO = "unit_no";
        public static final String COLUMN_NAME_PRODUCT_AREA = "product_area";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_SELLER_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_CODE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_ITEM_NAME, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_ITEM_SIZE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_UNIT_NO, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PRODUCT_AREA, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

    public static abstract class CategoryEntry extends Entry {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PARENT = "parent";
        public static final String COLUMN_NAME_IMAGE_1 = "image_1";
        public static final String COLUMN_NAME_IMAGE_2 = "image_2";
        public static final String COLUMN_NAME_IMAGE_3 = "image_3";
        public static final String COLUMN_NAME_IMAGE_4 = "image_4";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_SELLER_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_TITLE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PARENT, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_IMAGE_1, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_2, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_3, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_4, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_STATUS, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

    public static abstract class CommodityEntry extends Entry {
        public static final String TABLE_NAME = "commodity";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_IMAGE_1 = "image_1";
        public static final String COLUMN_NAME_IMAGE_2 = "image_2";
        public static final String COLUMN_NAME_IMAGE_3 = "image_3";
        public static final String COLUMN_NAME_IMAGE_4 = "image_4";
        public static final String COLUMN_NAME_IMAGE_5 = "image_5";
        public static final String COLUMN_NAME_IMAGE_6 = "image_6";
        public static final String COLUMN_NAME_SUPPORT_RETURN = "support_return";
        public static final String COLUMN_NAME_IN_DISCOUNT = "in_discount";
        public static final String COLUMN_NAME_IN_STOCK = "in_stock";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_SELLER_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_BARCODE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_TITLE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_DESCRIPTION, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PRICE, ColumnType.REAL_TYPE),
                new Column(COLUMN_NAME_THUMBNAIL, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_1, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_2, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_3, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_4, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_5, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_6, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_SUPPORT_RETURN, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_IN_DISCOUNT, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_IN_STOCK, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_CATEGORY_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_STATUS, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

    public static abstract class ExpressmanEntry extends Entry {
        public static final String TABLE_NAME = "expressman";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PSWD = "pswd";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_NAME, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PSWD, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_THUMBNAIL, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PHONE, ColumnType.TEXT_TYPE),
        };
    }

    public static abstract class MessageEntry extends Entry {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_SENDER = "sender";
        public static final String COLUMN_NAME_RECEIVER = "receiver";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_TYPE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_CONTENT, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_SENDER, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_RECEIVER, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_STATUS, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

    public static abstract class OrderEntry extends Entry {
        public static final String TABLE_NAME = "t_order";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_AGENCY_ID = "agency_id";
        public static final String COLUMN_NAME_SELLER_ID = "seller_id";
        public static final String COLUMN_NAME_COMMODITY_ID = "commodity_id";
        public static final String COLUMN_NAME_EXPRESSMAN_ID = "expressman_id";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_PAYMENT = "payment";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_AGENCY_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_SELLER_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_COMMODITY_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_EXPRESSMAN_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_AMOUNT, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_PAYMENT, ColumnType.REAL_TYPE),
                new Column(COLUMN_NAME_STATUS, ColumnType.TEXT_TYPE),
        };
    }

    public static abstract class SellerEntry extends Entry {
        public static final String TABLE_NAME = "seller";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PSWD = "pswd";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_IMAGE_1 = "image_1";
        public static final String COLUMN_NAME_IMAGE_2 = "image_2";
        public static final String COLUMN_NAME_IMAGE_3 = "image_3";
        public static final String COLUMN_NAME_IMAGE_4 = "image_4";
        public static final String COLUMN_NAME_PAYMENT_ID = "payment_id";
        public static final String COLUMN_NAME_PAYMENT_BANK = "payment_bank";
        public static final String COLUMN_NAME_PAYMENT_CARD = "payment_card";
        public static final String COLUMN_NAME_MODIFY_TIME = "modify_time";
        public static final Column[] COLUMNS = new Column[] {
                new Column(COLUMN_NAME_ID, ColumnType.INTEGER_TYPE),
                new Column(COLUMN_NAME_TITLE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PSWD, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_ADDRESS, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PHONE, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_THUMBNAIL, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_1, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_2, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_3, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_IMAGE_4, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PAYMENT_ID, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PAYMENT_BANK, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_PAYMENT_CARD, ColumnType.TEXT_TYPE),
                new Column(COLUMN_NAME_MODIFY_TIME, ColumnType.INTEGER_TYPE),
        };
    }

}