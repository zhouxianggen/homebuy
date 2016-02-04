package com.hg.www.selller.data.define;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类目列表
 */
public class CommodityCategoryList implements JsonSerializable {
    public List<CommodityCategory> categories = new ArrayList<>();

    @Override
    public Object parse(JSONObject jsonObject) {
        return null;
    }

    @Override
    public Object parse(String str) {
        return null;
    }

    @Override
    public String serialize() {
        return "";
    }
}
