# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from mysql_api import mysql
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from errors import *

class SingleResourceApi(object):
    
    def __init__(self):
        self.entry = None

    def get(self, id):
        resp = {}
        where = "WHERE %s='%s'" % (self.entry.COLUMN_NAME_ID, id)
        rs = mysql.select(self.entry.TABLE_NAME, self.entry.COLUMNS, where)
        if rs == None or len(rs) != 1:
            resp['status'] = SERVER_MYSQL_ERROR
        else:
            d = {self.entry.COLUMNS[i]:rs[0][i] for i in range(len(self.entry.COLUMNS))}
            resp['status'] = OK
            resp[self.entry.NAME] = d
            
        return json.dumps(resp, ensure_ascii=False)
        
    def post(data):
        resp = {}
        try:
            obj = json.loads(data)
            d = {col:obj[col] for col in self.entry.COLUMNS if col in obj}
            kvs = d.items()
            ks = ', '.join([x[0] for x in kvs])
            ss = ', '.join(['%s' for x in kvs])
            sql = "REPLACE INTO %s (%s) VALUES (%s)" % (self.entry.TABLE_NAME, ks, ss)
            args = tuple([x[1] for x in kvs])
            r = mysql.execute(sql, args)
            if r == -1:
                resp['status'] = SERVER_MYSQL_ERROR
            else:
                return self.get(s['id'])
        except Exception as e:
            resp['status'] = INPUT_DATA_ERROR
            resp['error'] = str(e)
            
        return json.dumps(resp, ensure_ascii=False)
        
