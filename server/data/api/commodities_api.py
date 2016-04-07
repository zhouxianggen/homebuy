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

class CommoditiesApi(MultiResourceApi):
    
    def __init__(self):
        self.entry = CommodityEntry

    def get(self, seller_id, if_modified_since, count):
        resp = {}

        if not if_modified_since.isdigit():
            resp['status'] = REQUEST_PARAM_ERROR
            return json.dumps(resp, ensure_ascii=False)
        if_modified_since = int(if_modified_since)
        if not count.isdigit() or int(count) <= 0:
            resp['status'] = REQUEST_PARAM_ERROR
            return json.dumps(resp, ensure_ascii=False)
        count = int(count)

        where = "WHERE %s='%s' AND %s>=%d" % (self.entry.COLUMN_NAME_SELLER_ID, seller_id, self.entry.COLUMN_NAME_MODIFY_TIME, if_modified_since)
        other = "ORDER BY %s LIMIT %d" % (self.entry.COLUMN_NAME_MODIFY_TIME, count)
        rows = mysql.select(self.entry.TABLE_NAME, self.entry.COLUMNS, where, other)
        if rows == None:
            resp['status'] = SERVER_MYSQL_ERROR
            return json.dumps(resp, ensure_ascii=False)
            
        resources = []
        for r in rows:
            d = {self.entry.COLUMNS[i]:r[i] for i in range(len(self.entry.COLUMNS))}
            resources.append(d)
        resp['status'] = OK
        resp['commodities'] = resources
            
        return json.dumps(resp, ensure_ascii=False)

instance = CommoditiesApi()

