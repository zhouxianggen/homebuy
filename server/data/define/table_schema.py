# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

class CommmodityEntry(object):
	TABLE_NAME = 'commodity'
	COLUMNS = ['id', 'seller_id', 'barcode', 'title', 'description', 'price', 'thumbnail', 'iamge_1', 'image_2', 'image_3', 'image_4']
	COLUMNS += ['image_5', 'image_6', 'image_7', 'image_8', 'support_return', 'in_discount', 'in_stock', 'category']
	COLUMNS += ['weekly_sales', 'weekly_returns', 'monthly_sales', 'monthly_returns']
	

