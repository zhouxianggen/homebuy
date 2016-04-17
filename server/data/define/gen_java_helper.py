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
    columns = ['COLUMN_NAME', 'DATA_TYPE', 'COLUMN_KEY']
    where = "where TABLE_SCHEMA='%s' and TABLE_NAME='%s'" % (db_name, table_name)
    rows = mysql.select('INFORMATION_SCHEMA.COLUMNS', columns, where)
    return rows

def gen_schema(table_name, columns, entry_name):
    s  = '    private static final String SQL_CREATE_%s_TABLE =\n' % table_name.upper()
    s += '            "CREATE TABLE " + TableSchema.%s.TABLE_NAME + " (" +\n' % entry_name
    pris = []
    pri = '                    PRIMARY + " (" +\n'
    for idx, v in enumerate(columns):
        n, t, k = v
        if t in ['tinyint', 'int', 'bigint']:
            tn = 'INTEGER_TYPE'
        elif t in ['double', 'float']:
            tn = 'REAL_TYPE'
        elif t in ['varchar', 'text']:
            tn = 'TEXT_TYPE'
        else:
            print 'error type: ', table_name, t
            continue
        if k == 'PRI':
            pris.append('                        TableSchema.%s.COLUMN_NAME_%s' % (entry_name, n.upper()))
        s += '                    TableSchema.%s.COLUMN_NAME_%s + %s + COMMA_SEP +\n' % (entry_name, n.upper(), tn)
    pri += ' + COMMA_SEP + \n'.join(pris) + ' + ") " + \n'
    s += pri
    s += '            " )";\n'
    s += '    private static final String SQL_DELETE_%s_TABLE =\n' % table_name.upper()
    s += '            "DROP TABLE IF EXISTS " + TableSchema.%s.TABLE_NAME;\n\n' % entry_name
    return s

HEADER = "package %s;" % package_name
HEADER += """

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "seller.db";
    private static final String PRIMARY = " PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
"""

TAIL = """
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
"""

content = HEADER

for tn, en in tables:
    columns = get_columns(db, tn)
    content += gen_schema(tn, columns, en)

content += '    public void onCreate(SQLiteDatabase db) {\n'
for tn, en in tables:
    content += '        db.execSQL(SQL_CREATE_%s_TABLE);\n' % tn.upper()
content += '    }\n\n'

content += '    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {\n'
for tn, en in tables:
    content += '        db.execSQL(SQL_DELETE_%s_TABLE);\n' % tn.upper()
content += '        onCreate(db);\n'
content += '    }\n\n'

content += TAIL

codecs.open('DbHelper.java', 'wb', 'utf8').write(content)

