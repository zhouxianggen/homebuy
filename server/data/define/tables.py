# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"


tables_for_seller = []
tables_for_seller.append(('barcode', 'BarcodeEntry'))
tables_for_seller.append(('category', 'CategoryEntry'))
tables_for_seller.append(('commodity', 'CommodityEntry'))
tables_for_seller.append(('expressman', 'ExpressmanEntry'))
tables_for_seller.append(('message', 'MessageEntry'))
tables_for_seller.append(('order', 'OrderEntry'))
tables_for_seller.append(('seller', 'SellerEntry'))

tables_for_buyer = []
tables_for_buyer.append(('buyer', 'BuyerEntry'))
tables_for_buyer.append(('order', 'OrderEntry'))
tables_for_buyer.append(('seller', 'SellerEntry'))
tables_for_buyer.append(('commodity', 'CommodityEntry'))

package_name = 'com.hg.www.buyer.data.db'
tables = tables_for_buyer
