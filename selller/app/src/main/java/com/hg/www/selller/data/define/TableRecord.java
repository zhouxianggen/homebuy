package com.hg.www.selller.data.define;

import android.database.Cursor;
import android.util.Log;

import com.hg.www.selller.data.db.TableSchema;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本数据操作对象
 */
public class TableRecord {
    protected String TAG = TableRecord.class.getSimpleName();
    protected Map<String, Object> properties = new HashMap<String, Object>();
    protected TableSchema.Column[] columns = new TableSchema.Column[]{};

    public String toString() {
        String s = "{ ";
        for (int i = 0; i < this.columns.length; i++) {
            TableSchema.Column column = columns[i];
            if (i > 0) {
                s += ", ";
            }
            if (column.type == TableSchema.ColumnType.TEXT_TYPE) {
                try {
                    String value = URLEncoder.encode(getStringProperty(column.name), "utf-8");
                    s += String.format("\"%s\": \"%s\"", column.name, value);
                } catch (java.io.UnsupportedEncodingException e) {
                    s += String.format("\"%s\": \"%s\"", column.name, getStringProperty(column.name));
                }
            } else if (column.type == TableSchema.ColumnType.INTEGER_TYPE) {
                s += String.format("\"%s\": %d", column.name, getIntProperty(column.name));
            } else if (column.type == TableSchema.ColumnType.REAL_TYPE) {
                s += String.format("\"%s\": %f", column.name, getFloatProperty(column.name));
            }
        }
        s += " }";
        return s;
    }

    public boolean parseFromCursor(Cursor cursor) {
        properties = new HashMap<String, Object>();

        for (TableSchema.Column column : columns) {
            Log.d(TAG, String.format("parse column [%s]", column.name));
            if (column.type == TableSchema.ColumnType.TEXT_TYPE) {
                properties.put(column.name, (Object) cursor.getString(cursor.getColumnIndexOrThrow(column.name)));
            } else if (column.type == TableSchema.ColumnType.INTEGER_TYPE) {
                properties.put(column.name, (Object) cursor.getInt(cursor.getColumnIndexOrThrow(column.name)));
            } else if (column.type == TableSchema.ColumnType.REAL_TYPE) {
                properties.put(column.name, (Object) cursor.getFloat(cursor.getColumnIndexOrThrow(column.name)));
            } else {
                Log.e(TAG, "unknown column type");
                return false;
            }
        }

        Log.d(TAG, String.format("object is [%s]", toString()));
        return true;
    }

    public String getStringProperty(String key) {
        if (properties.containsKey(key)) {
            return (String) properties.get(key);
        } else {
            Log.d(TAG, String.format("get string property of [%s], key not exist", key));
            return "";
        }
    }

    public int getIntProperty(String key) {
        if (properties.containsKey(key)) {
            return (Integer) properties.get(key);
        } else {
            Log.d(TAG, String.format("get int property of [%s], key not exist", key));
            return 0;
        }
    }

    public float getFloatProperty(String key) {
        if (properties.containsKey(key)) {
            return (Float) properties.get(key);
        } else {
            Log.d(TAG, String.format("get float property of [%s], key not exist", key));
            return 0;
        }
    }

    public void setStringProperty(String key, String value) {
        properties.put(key, (Object) value);
    }

    public void setIntProperty(String key, int value) {
        properties.put(key, (Object) value);
    }

    public void setFloatProperty(String key, float value) {
        properties.put(key, (Object) value);
    }
}
