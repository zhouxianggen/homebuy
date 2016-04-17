# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from mysql_api import mysql
from multi_resource_api import MultiResourceApi
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *
from errors import *

class OrdersApi(MultiResourceApi):
    
    def __init__(self):
        self.entry = OrderEntry

    def get_buyer_unfinished_orders(self, buyer_id):
        resp = {}
        where = "WHERE %s='%s'" % (self.entry.COLUMN_NAME_BUYER_ID, buyer_id) 
        other = "ORDER BY %s DESC" % (self.entry.COLUMN_NAME_MODIFY_TIME)
        rows = mysql.select(self.entry.TABLE_NAME, self.entry.COLUMNS, where, other)
        if rows == None:
            resp['status'] = SERVER_MYSQL_ERROR
            return json.dumps(resp, ensure_ascii=False)
            
        objects = []
        for r in rows:
            d = {self.entry.COLUMNS[i]:r[i] for i in range(len(self.entry.COLUMNS))}
            objects.append(d)
        
        resp['status'] = OK
        resp['orders'] = objects
            
        return json.dumps(resp, ensure_ascii=False)

instance = OrdersApi()

