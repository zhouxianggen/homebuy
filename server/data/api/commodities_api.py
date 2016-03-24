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

class CommoditiesApi(object):
	@staticmethod
	def get(seller_id, if_modified_since):
		resp = {}
		where = "WHERE %s='%s' AND %s='%s'" % (CommoidtyEntry.COLUMN_NAME_SELLER_ID, seller_id)
		rs = mysql.select(CommoidtyEntry.TABLE_NAME, CommoidtyEntry.COLUMNS, where)
		if rs == None:
			resp['status'] = SERVER_MYSQL_ERROR
		else:
			hits = []
			remains = []
			for r in rs:
				d = {CommoidtyEntry.COLUMNS[i]:r[i] for i in range(len(CommoidtyEntry.COLUMNS))}
				if d[CommoidtyEntry.COLUMN_NAME_MODIFY_TIME] > if_modified_since:
					hits.append(d)
				else:
					remains.append(d)
			resp['status'] = OK
			resp['commodities'] = hits
			resp['remain_count'] = len(remains)
			
		return json.dumps(resp, ensure_ascii=False)
		