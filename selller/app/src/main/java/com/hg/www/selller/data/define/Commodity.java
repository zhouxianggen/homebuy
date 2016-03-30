package com.hg.www.selller.data.define;

import com.hg.www.selller.data.db.TableSchema;

import java.util.ArrayList;

/**
 * 商品数据格式
 */
public class Commodity extends TableRecord {
    private static TableSchema.Column[] columns = TableSchema.CommodityEntry.COLUMNS;

    public void deleteImage(String path) {

    }

    public ArrayList<String> getImages() {
        ArrayList<String> images = new ArrayList<>();
        return images;
    }

    public void addImage(String path) {

    }
}
