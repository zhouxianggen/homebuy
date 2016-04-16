# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import codecs
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../api' % CWD)
from mysql_api import mysql, db
from tables import tables, package_name

def get_columns(db_name, table_name):
    columns = ['COLUMN_NAME', 'DATA_TYPE']
    where = "where TABLE_SCHEMA='%s' and TABLE_NAME='%s'" % (db_name, table_name)
    rows = mysql.select('INFORMATION_SCHEMA.COLUMNS', columns, where)
    return rows

def gen_schema(table_name, columns, class_name):
    s  = '    public static abstract class %s extends Entry {\n' % class_name
    s += '        public static final String TABLE_NAME = "%s";\n' % (table_name if table_name != 'order' else 't_order')
    for n,t in columns:
        s += '        public static final String COLUMN_NAME_%s = "%s";\n' % (n.upper(), n)
    s += '        public static final Column[] COLUMNS = new Column[] {\n'
    for n,t in columns:
        if t in ['tinyint', 'int', 'bigint']:
            tn = 'INTEGER_TYPE'
        elif t in ['double', 'float']:
            tn = 'REAL_TYPE'
        elif t in ['varchar', 'text']:
            tn = 'TEXT_TYPE'
        else:
            print 'error type: ', table_name, t
            continue
        s += '                new Column(COLUMN_NAME_%s, ColumnType.%s),\n' % (n.upper(), tn)
    s += '        };\n'
    s += '    }\n\n'
    return s

HEADER = "package %s" % package_name
HEADER += """

import android.provider.BaseColumns;

public final class TableSchema {
    public TableSchema() {}

    public enum ColumnType {
        TEXT_TYPE,
        INTEGER_TYPE,
        REAL_TYPE
    }
    
    public static class Column {
        public String name = "";
        public ColumnType type = ColumnType.TEXT_TYPE;
        
        public Column(String name, ColumnType type) {
            this.name = name;
            this.type = type;
        }
    }

    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "";
        public static final Column[] COLUMNS = new Column[] {};
    }

"""

TAIL = """}"""

content = HEADER

for tn, cn in tables:
    columns = get_columns(db, tn)
    content += gen_schema(tn, columns, cn)

content += TAIL

codecs.open('TableSchema.java', 'wb', 'utf8').write(content)

