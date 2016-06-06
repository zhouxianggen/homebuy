# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from mysql_api import mysql, get_instance
from single_resource_api import SingleResourceApi
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *
from errors import *

class BuyerApi(SingleResourceApi):
    
    def __init__(self):
        self.entry = BuyerEntry

    def get(self, id):
        resp = {}
        where = "WHERE %s='%s'" % (self.entry.COLUMN_NAME_ID, id) 
        rows = get_instance().select(self.entry.TABLE_NAME, self.entry.COLUMNS, where)
        if len(rows) != 1:
            resp['status'] = SERVER_MYSQL_ERROR
            return json.dumps(resp, ensure_ascii=False)
        
        resp['buyer'] = {self.entry.COLUMNS[i]:rows[0][i] for i in range(len(self.entry.COLUMNS))}
        resp['status'] = OK
            
        return json.dumps(resp, ensure_ascii=False)

    def post(self, buyer):
        print 'BuyerApi post: [%s]' % buyer
        resp = {}
        try:
            d = json.loads(buyer)
            d[self.entry.COLUMN_NAME_MODIFY_TIME] = int(time.time())
            cs = [x for x in d.keys()]
            ps = ', '.join(cs)
            ss = ', '.join(['%s' for c in cs])
            sql = 'REPLACE INTO %s (%s) VALUES (%s)' % (self.entry.TABLE_NAME, ps, ss)
            args = tuple([urllib.unquote(str(d[c])) for c in cs])
            r = mysql.execute(sql, args)
            if r != 1:
                print 'mysql execute return %d' % r
                resp['status'] = SERVER_MYSQL_ERROR
            else:
                resp['status'] = OK
        except Exception as e:
            print e
            resp['status'] = REQUEST_PARAM_ERROR
        return json.dumps(resp, ensure_ascii=False)

instance = BuyerApi()
