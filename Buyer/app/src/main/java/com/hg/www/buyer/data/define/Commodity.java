package com.hg.www.buyer.data.define;

import com.hg.www.buyer.data.db.TableSchema;

import java.util.ArrayList;


public class Commodity extends TableRecord {
    {
        TAG = Commodity.class.getSimpleName();
        columns = TableSchema.CommodityEntry.COLUMNS;
    }

    private String[] imageKeys = new String[] {
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_1,
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_2,
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_3,
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_4,
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_5,
            TableSchema.CommodityEntry.COLUMN_NAME_IMAGE_6
    };

    public ArrayList<String> getImages() {
        ArrayList<String> images = new ArrayList<>();
        for (String k : imageKeys) {
            String path = getStringProperty(k);
            if (!path.isEmpty()) {
                images.add(path);
            }
        }
        return images;
    }
}
