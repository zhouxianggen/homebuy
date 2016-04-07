package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

public class Expressman extends TableRecord {
    {
        TAG = Expressman.class.getSimpleName();
        columns = TableSchema.ExpressmanEntry.COLUMNS;
    }
}
