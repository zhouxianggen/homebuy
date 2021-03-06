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

host1 = '10.99.20.92'
port1 = 3306
user1 = 'root'
pswd1 = 'shenma123'
db1 = 'test7'

host2 = 'hg2.c7dsdrfy4wfo.us-west-2.rds.amazonaws.com'
port2 = 3306
user2 = 'zhouxg'
pswd2 = 'justaguest'
db2 = 'homebuy'

mysql_schema = MySqlApi(host2, port2, user2, pswd2, 'INFORMATION_SCHEMA')

#mysql_test = MySqlApi(host1, port1, user1, pswd1, db1)
mysql_online = MySqlApi(host2, port2, user2, pswd2, db2)

#mysql = mysql_test
mysql = mysql_online
db = db2

def get_instance():
    return MySqlApi(host2, port2, user2, pswd2, db2)


