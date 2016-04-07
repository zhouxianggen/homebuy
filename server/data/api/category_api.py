# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import time
import urllib
import json
from mysql_api import mysql
from single_resource_api import SingleResourceApi
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *
from errors import *

class CategoryApi(SingleResourceApi):
    def __init__(self):
        self.entry = CategoryEntry

    def post(self, category):
        print 'CategoryApi post: [%s]' % category
        resp = {}
        try:
            d = json.loads(category)
            d[self.entry.COLUMN_NAME_MODIFY_TIME] = int(time.time())
            cs = [x for x in d.keys() if x != self.entry.COLUMN_NAME_ID]
            id = d[self.entry.COLUMN_NAME_ID]
            if id == 0: # add category
                ps = ', '.join(cs)
                ss = ', '.join(['%s' for c in cs])
                sql = 'INSERT INTO %s (%s) VALUES (%s)' % (self.entry.TABLE_NAME, ps, ss)
            else: # replace
                kvs = ', '.join(['%s=%%s' % (c) for c in cs])
                sql = 'UPDATE %s SET %s WHERE %s=%d' % (self.entry.TABLE_NAME, kvs, self.entry.COLUMN_NAME_ID, id)
            print sql
            args = tuple([urllib.unquote(str(d[c])) for c in cs])
            print args
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
instance = CategoryApi()

