# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import codecs
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../api' % CWD)
from mysql_api import mysql_test, test_db

def get_columns(db_name, table_name):
    columns = ['COLUMN_NAME']
    where = "where TABLE_SCHEMA='%s' and TABLE_NAME='%s'" % (db_name, table_name)
    rows = mysql_test.select('INFORMATION_SCHEMA.COLUMNS', columns, where)
    return [x[0] for x in rows]

def gen_schema(table_name, column_names, class_name):
    wlns = []
    wlns.append('class %s(object):' % class_name)
    wlns.append('\tNAME = "%s"\n' % table_name)
    wlns.append('\tTABLE_NAME = "%s"\n' % table_name)
    ns = []
    for n in column_names:
        wlns.append('\tCOLUMN_NAME_%s = "%s"' % (n.upper(), n))
        ns.append('COLUMN_NAME_%s' % n.upper())
    wlns.append('\n\tCOLUMNS = [%s]' % ', '.join(ns))
    return '\n'.join(wlns) + '\n'

tables = []
tables.append(('agency', 'AgencyEntry'))
tables.append(('barcode', 'BarcodeEntry'))
tables.append(('category', 'CategoryEntry'))
tables.append(('commodity', 'CommodityEntry'))
tables.append(('expressman', 'ExpressmanEntry'))
tables.append(('message', 'MessageEntry'))
tables.append(('order', 'OrderEntry'))
tables.append(('seller', 'SellerEntry'))

wlns = []
wlns.append('#!/usr/bin/env python\n')

for tn, cn in tables:
    columns = get_columns(test_db, tn)
    wlns.append('#')
    wlns.append('# schema for table %s' % tn)
    wlns.append('#')
    wlns.append(gen_schema(tn, columns, cn))

codecs.open('table_schema.py', 'wb', 'utf8').write('\n'.join(wlns))

