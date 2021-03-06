#!/usr/bin/env python

#
# schema for table buyer
#
class BuyerEntry(object):
	NAME = "buyer"

	TABLE_NAME = "buyer"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_NAME = "name"
	COLUMN_NAME_PHONE = "phone"
	COLUMN_NAME_IMEI = "imei"
	COLUMN_NAME_ADDRESS_1 = "address_1"
	COLUMN_NAME_ADDRESS_2 = "address_2"
	COLUMN_NAME_ADDRESS_3 = "address_3"
	COLUMN_NAME_ADDRESS_4 = "address_4"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_PHONE, COLUMN_NAME_IMEI, COLUMN_NAME_ADDRESS_1, COLUMN_NAME_ADDRESS_2, COLUMN_NAME_ADDRESS_3, COLUMN_NAME_ADDRESS_4, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]

#
# schema for table order
#
class OrderEntry(object):
	NAME = "order"

	TABLE_NAME = "order"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_BUYER_ID = "buyer_id"
	COLUMN_NAME_AGENCY_ID = "agency_id"
	COLUMN_NAME_SELLER_ID = "seller_id"
	COLUMN_NAME_COMMODITY_ID = "commodity_id"
	COLUMN_NAME_EXPRESSMAN_ID = "expressman_id"
	COLUMN_NAME_AMOUNT = "amount"
	COLUMN_NAME_PAYMENT = "payment"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_BUYER_ID, COLUMN_NAME_AGENCY_ID, COLUMN_NAME_SELLER_ID, COLUMN_NAME_COMMODITY_ID, COLUMN_NAME_EXPRESSMAN_ID, COLUMN_NAME_AMOUNT, COLUMN_NAME_PAYMENT, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]

#
# schema for table seller
#
class SellerEntry(object):
	NAME = "seller"

	TABLE_NAME = "seller"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_TITLE = "title"
	COLUMN_NAME_PSWD = "pswd"
	COLUMN_NAME_ADDRESS = "address"
	COLUMN_NAME_PHONE = "phone"
	COLUMN_NAME_THUMBNAIL = "thumbnail"
	COLUMN_NAME_IMAGE_1 = "image_1"
	COLUMN_NAME_IMAGE_2 = "image_2"
	COLUMN_NAME_IMAGE_3 = "image_3"
	COLUMN_NAME_IMAGE_4 = "image_4"
	COLUMN_NAME_PAYMENT_ID = "payment_id"
	COLUMN_NAME_PAYMENT_BANK = "payment_bank"
	COLUMN_NAME_PAYMENT_CARD = "payment_card"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_TITLE, COLUMN_NAME_PSWD, COLUMN_NAME_ADDRESS, COLUMN_NAME_PHONE, COLUMN_NAME_THUMBNAIL, COLUMN_NAME_IMAGE_1, COLUMN_NAME_IMAGE_2, COLUMN_NAME_IMAGE_3, COLUMN_NAME_IMAGE_4, COLUMN_NAME_PAYMENT_ID, COLUMN_NAME_PAYMENT_BANK, COLUMN_NAME_PAYMENT_CARD, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]

#
# schema for table seller_service_area
#
class SellerServiceAreaEntry(object):
	NAME = "seller_service_area"

	TABLE_NAME = "seller_service_area"

	COLUMN_NAME_SELLER_ID = "seller_id"
	COLUMN_NAME_AREA = "area"

	COLUMNS = [COLUMN_NAME_SELLER_ID, COLUMN_NAME_AREA]

#
# schema for table commodity
#
class CommodityEntry(object):
	NAME = "commodity"

	TABLE_NAME = "commodity"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_SELLER_ID = "seller_id"
	COLUMN_NAME_BARCODE = "barcode"
	COLUMN_NAME_TITLE = "title"
	COLUMN_NAME_DESCRIPTION = "description"
	COLUMN_NAME_PRICE = "price"
	COLUMN_NAME_THUMBNAIL = "thumbnail"
	COLUMN_NAME_IMAGE_1 = "image_1"
	COLUMN_NAME_IMAGE_2 = "image_2"
	COLUMN_NAME_IMAGE_3 = "image_3"
	COLUMN_NAME_IMAGE_4 = "image_4"
	COLUMN_NAME_IMAGE_5 = "image_5"
	COLUMN_NAME_IMAGE_6 = "image_6"
	COLUMN_NAME_SUPPORT_RETURN = "support_return"
	COLUMN_NAME_IN_DISCOUNT = "in_discount"
	COLUMN_NAME_IN_STOCK = "in_stock"
	COLUMN_NAME_CATEGORY_ID = "category_id"
	COLUMN_NAME_CATEGORY = "category"
	COLUMN_NAME_WEEKLY_SALES = "weekly_sales"
	COLUMN_NAME_WEEKLY_RETURNS = "weekly_returns"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_SELLER_ID, COLUMN_NAME_BARCODE, COLUMN_NAME_TITLE, COLUMN_NAME_DESCRIPTION, COLUMN_NAME_PRICE, COLUMN_NAME_THUMBNAIL, COLUMN_NAME_IMAGE_1, COLUMN_NAME_IMAGE_2, COLUMN_NAME_IMAGE_3, COLUMN_NAME_IMAGE_4, COLUMN_NAME_IMAGE_5, COLUMN_NAME_IMAGE_6, COLUMN_NAME_SUPPORT_RETURN, COLUMN_NAME_IN_DISCOUNT, COLUMN_NAME_IN_STOCK, COLUMN_NAME_CATEGORY_ID, COLUMN_NAME_CATEGORY, COLUMN_NAME_WEEKLY_SALES, COLUMN_NAME_WEEKLY_RETURNS, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]

#
# schema for table barcode
#
class BarcodeEntry(object):
	NAME = "barcode"

	TABLE_NAME = "barcode"

	COLUMN_NAME_SELLER_ID = "seller_id"
	COLUMN_NAME_CODE = "code"
	COLUMN_NAME_ITEM_NAME = "item_name"
	COLUMN_NAME_ITEM_SIZE = "item_size"
	COLUMN_NAME_UNIT_NO = "unit_no"
	COLUMN_NAME_PRODUCT_AREA = "product_area"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_SELLER_ID, COLUMN_NAME_CODE, COLUMN_NAME_ITEM_NAME, COLUMN_NAME_ITEM_SIZE, COLUMN_NAME_UNIT_NO, COLUMN_NAME_PRODUCT_AREA, COLUMN_NAME_MODIFY_TIME]

#
# schema for table category
#
class CategoryEntry(object):
	NAME = "category"

	TABLE_NAME = "category"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_SELLER_ID = "seller_id"
	COLUMN_NAME_TITLE = "title"
	COLUMN_NAME_PARENT = "parent"
	COLUMN_NAME_TYPE = "type"
	COLUMN_NAME_IMAGE_1 = "image_1"
	COLUMN_NAME_IMAGE_2 = "image_2"
	COLUMN_NAME_IMAGE_3 = "image_3"
	COLUMN_NAME_IMAGE_4 = "image_4"
	COLUMN_NAME_ITEM_COUNT = "item_count"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_SELLER_ID, COLUMN_NAME_TITLE, COLUMN_NAME_PARENT, COLUMN_NAME_TYPE, COLUMN_NAME_IMAGE_1, COLUMN_NAME_IMAGE_2, COLUMN_NAME_IMAGE_3, COLUMN_NAME_IMAGE_4, COLUMN_NAME_ITEM_COUNT, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]

#
# schema for table expressman
#
class ExpressmanEntry(object):
	NAME = "expressman"

	TABLE_NAME = "expressman"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_NAME = "name"
	COLUMN_NAME_PSWD = "pswd"
	COLUMN_NAME_THUMBNAIL = "thumbnail"
	COLUMN_NAME_PHONE = "phone"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_NAME, COLUMN_NAME_PSWD, COLUMN_NAME_THUMBNAIL, COLUMN_NAME_PHONE]

#
# schema for table message
#
class MessageEntry(object):
	NAME = "message"

	TABLE_NAME = "message"

	COLUMN_NAME_ID = "id"
	COLUMN_NAME_TYPE = "type"
	COLUMN_NAME_CONTENT = "content"
	COLUMN_NAME_SENDER = "sender"
	COLUMN_NAME_RECEIVER = "receiver"
	COLUMN_NAME_STATUS = "status"
	COLUMN_NAME_MODIFY_TIME = "modify_time"

	COLUMNS = [COLUMN_NAME_ID, COLUMN_NAME_TYPE, COLUMN_NAME_CONTENT, COLUMN_NAME_SENDER, COLUMN_NAME_RECEIVER, COLUMN_NAME_STATUS, COLUMN_NAME_MODIFY_TIME]
