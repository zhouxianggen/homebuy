package com.hg.www.selller.data.define;

import android.database.Cursor;

import com.hg.www.selller.data.db.TableSchema;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本数据操作对象
 */
public class TableRecord {
    private Map<String, Object> properties;
    private static TableSchema.Column[] columns = new TableSchema.Column[]{};

    public String toString() {
        String s = "{ ";
        for (int i = 0; i < columns.length; i++) {
            TableSchema.Column column = columns[i];
            if (i > 0) {
                s += ", ";
            }
            if (column.type == TableSchema.ColumnType.TEXT_TYPE) {
                s += String.format("\"%s\": \"%s\"", column.name, getStringProperty(column.name));
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
            if (column.type == TableSchema.ColumnType.TEXT_TYPE) {
                properties.put(column.name, (Object) cursor.getString(cursor.getColumnIndexOrThrow(column.name)));
            } else if (column.type == TableSchema.ColumnType.INTEGER_TYPE) {
                properties.put(column.name, (Object) cursor.getInt(cursor.getColumnIndexOrThrow(column.name)));
            } else if (column.type == TableSchema.ColumnType.REAL_TYPE) {
                properties.put(column.name, (Object) cursor.getFloat(cursor.getColumnIndexOrThrow(column.name)));
            } else {
                return false;
            }
        }
        return true;
    }

    public String getStringProperty(String key) {
        return (String) properties.get(key);
    }

    public int getIntProperty(String key) {
        return (Integer) properties.get(key);
    }

    public float getFloatProperty(String key) {
        return (Float) properties.get(key);
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
