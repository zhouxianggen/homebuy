# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import MySQLdb

class MySqlApi(object):
    def __init__(self, host, port, user, pswd, db, charset='utf8'):
        self.conn = MySQLdb.connect(host=host, port=port, user=user, passwd=pswd, db=db, charset=charset)
    
    def __del__(self):
        if self.conn:
            self.conn.close()
    
    def reset(self, host, port, user, pswd, db, charset='utf8'):
        self.conn = MySQLdb.connect(host=host, port=port, user=user, passwd=pswd, db=db, charset=charset)
    
    def execute(self, sql, args):
        try:
            r = self.conn.cursor().execute(sql, args)
            self.conn.commit()
            return r
        except Exception as e:
            self.conn.rollback()
            print e
            return -1
            
    def executemany(self, sql, args_array):
        try:
            r = self.conn.cursor().executemany(sql, args_array)
            self.conn.commit()
            return r
        except Exception as e:
            self.conn.rollback()
            print e
            return -1

    def select(self, table, columns, where='', other=''):
        try:
            sql = "SELECT %s FROM %s %s %s" % (','.join(columns), table, where, other)
            cur = self.conn.cursor()
            cur.execute(sql)
            return cur.fetchall()
        except Exception as e:
            print e
            return None

host = '10.99.20.92'
port = 3306
user = 'root'
pswd = 'shenma123'
test_db = 'test7'
mysql_test = MySqlApi(host, port, user, pswd, test_db)
mysql_schema = MySqlApi(host, port, user, pswd, 'INFORMATION_SCHEMA')
mysql = mysql_test
