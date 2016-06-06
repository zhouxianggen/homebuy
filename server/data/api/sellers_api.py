# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from mysql_api import mysql, get_instance
from multi_resource_api import MultiResourceApi
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *
from errors import *

class SellersApi(MultiResourceApi):
    
    def __init__(self):
        self.entry = SellerEntry

    def get_area_sellers(self, area):
        resp = {}

        columns = [SellerServiceAreaEntry.COLUMN_NAME_SELLER_ID]
        where = "WHERE %s='%s'" % (SellerServiceAreaEntry.COLUMN_NAME_AREA, area) 
        rows = get_instance().select(SellerServiceAreaEntry.TABLE_NAME, columns, where)
        if rows == None:
            resp['status'] = SERVER_MYSQL_ERROR
            return json.dumps(resp, ensure_ascii=False)
        ids = set([r[0] for r in rows])
        print ids
        if ids:    
            rows = mysql.select(self.entry.TABLE_NAME, self.entry.COLUMNS, '')
            if rows == None:
                resp['status'] = SERVER_MYSQL_ERROR
                return json.dumps(resp, ensure_ascii=False)
            objects = []
            for r in rows:
                d = {self.entry.COLUMNS[i]:r[i] for i in range(len(self.entry.COLUMNS))}
                if d[self.entry.COLUMN_NAME_ID] in ids:
                    objects.append(d)
        
        resp['status'] = OK
        resp['sellers'] = objects
            
        return json.dumps(resp, ensure_ascii=False)

instance = SellersApi()

