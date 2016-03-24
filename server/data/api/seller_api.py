# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from errors import *
from mysql_api import mysql
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *

class SellerApi(object):
	@staticmethod
	def get(id):
		resp = {}
		where = "WHERE %s='%s'" % (SellerEntry.COLUMN_NAME_ID, id)
		rs = mysql.select(SellerEntry.TABLE_NAME, SellerEntry.COLUMNS, where)
		if rs == None or len(rs) != -1:
			resp['status'] = SERVER_MYSQL_ERROR
		else:
			d = {SellerEntry.COLUMNS[i]:rs[0][i] for i in range(len(SellerEntry.COLUMNS))}
			resp['status'] = OK
			resp['seller'] = d
			
		return json.dumps(resp, ensure_ascii=False)
		
	@staticmethod
	def post(seller):
		resp = {}
		try:
			s = json.loads(seller)
			d = {col:s[col] for col in SellerEntry.COLUMNS if col in s}
			kvs = d.items()
			ks = ', '.join([x[0] for x in kvs])
			ss = ', '.join(['%s' for x in kvs])
			sql = "REPLACE INTO %s (%s) VALUES (%s)" % (SellerEntry.TABLE_NAME, ks, ss)
			args = tuple([x[1] for x in kvs])
			r = mysql.execute(sql, args)
			if r == -1:
				resp['status'] = SERVER_MYSQL_ERROR
			else:
				return self.get(s['id'])
		except Exception as e:
			resp['status'] = INPUT_DATA_ERROR
			
		return json.dumps(resp, ensure_ascii=False)
		