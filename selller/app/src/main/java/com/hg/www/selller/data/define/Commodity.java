package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

import java.util.ArrayList;

/**
 * 商品数据格式
 */
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

    public void deleteImage(String path) {
        for (String k : imageKeys) {
            String tmp = getStringProperty(k);
            if (tmp.equals(path)) {
                setStringProperty(k, "");
                break;
            }
        }
    }

    public ArrayList<String> deleteImages() {
        ArrayList<String> images = new ArrayList<>();
        for (String k : imageKeys) {
            setStringProperty(k, "");
        }
        return images;
    }

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

    public void addImage(String path) {
        for (String k : imageKeys) {
            String tmp = getStringProperty(k);
            if (tmp.equals(path)) {
                break;
            }
            if (tmp.isEmpty()) {
                setStringProperty(k, path);
                break;
            }
        }
    }
}
