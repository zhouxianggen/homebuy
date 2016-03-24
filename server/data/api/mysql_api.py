# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import MySQLdb

class MySqlApi(object):
	def __init__(self, host, port, db, user, pswd, charset='utf8'):
		self.conn = MySQLdb.connect(host=host, port=port, user=user, passwd=passwd, db=db, charset)
	
	def __del__(self):
		if self.conn:
			self.conn.close()
	
	def reset(self, host, port, db, user, pswd, charset='utf8'):
		self.conn = MySQLdb.connect(host=host, port=port, user=user, passwd=passwd, db=db, charset)
	
	def execute(self, sql, args):
        try:
            r = self.conn.cursor().execute(sql, args)
            self.conn.commit()
			return r
        except Exception as e:
            self.conn.rollback()
			return -1
			
	def executemany(self, sql, args_array):
        try:
            r = self.conn.cursor().executemany(sql, args_array)
            self.conn.commit()
			return r
        except Exception as e:
            self.conn.rollback()
			return -1

	def select(self, table, columns, where='', other=''):
		try:
			sql = "SELECT %s FROM %s %s %s" % (','.join(columns), table, where, other)
			self.conn.cursor().execute(sql)
			return cur.fetchall()
		except Exception as e:
			return None

mysql = MySqlApi()

        
