package com.hg.www.selller.data.define;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表
 */
public class CommodityList implements JsonSerializable {
    public List<Commodity> commodities = new ArrayList<>();

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
