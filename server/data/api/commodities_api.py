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

class CommoditiesApi(MultiResourceApi):
    
    def __init__(self):
        self.entry = CommodityEntry

    def get_seller_commodities(self, seller_id, if_modified_since, check):
        resp = {}

        if not if_modified_since.isdigit():
            resp['status'] = REQUEST_PARAM_ERROR
            return json.dumps(resp, ensure_ascii=False)
        if_modified_since = int(if_modified_since)
        
        check = int(check)

        where = "WHERE %s='%s'" % (self.entry.COLUMN_NAME_SELLER_ID, seller_id) 
        other = "ORDER BY %s" % (self.entry.COLUMN_NAME_MODIFY_TIME)
        rows = get_instance().select(self.entry.TABLE_NAME, self.entry.COLUMNS, where, other)
        if rows == None:
            resp['status'] = SERVER_MYSQL_ERROR
            return json.dumps(resp, ensure_ascii=False)
            
        objects = []
        num = 0
        for r in rows:
            d = {self.entry.COLUMNS[i]:r[i] for i in range(len(self.entry.COLUMNS))}
            if d[self.entry.COLUMN_NAME_MODIFY_TIME] > if_modified_since:
                objects.append(d)
            else:
                num += d[self.entry.COLUMN_NAME_ID]
        
        resp['status'] = OK if not check or num == check else CHECK_ERROR 
        resp['commodities'] = objects
            
        return json.dumps(resp, ensure_ascii=False)

instance = CommoditiesApi()

