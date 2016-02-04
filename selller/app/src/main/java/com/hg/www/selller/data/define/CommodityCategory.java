package com.hg.www.selller.data.define;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 商品类目数据格式.
 */
public class CommodityCategory implements JsonSerializable {
    public String id = "";
    public String seller_id = "";
    public String title = "";
    public int item_count = 0;
    public int item_sales = 0;
    public String super_id = "";

    @Override
    public Object parse(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            seller_id = jsonObject.getString("seller_id");
            title = jsonObject.getString("title");
            item_count = jsonObject.getInt("item_count");
            item_sales = jsonObject.getInt("item_sales");
            super_id = jsonObject.getString("super_id");
        } catch (JSONException e) {
            return null;
        }
        return this;
    }

    @Override
    public Object parse(String str) {
        return true;
    }

    @Override
    public String serialize() {
        return "";
    }
}
