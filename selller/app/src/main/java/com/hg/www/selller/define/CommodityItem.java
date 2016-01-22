package com.hg.www.selller.define;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommodityItem {
    public String seller_id = "";
    public String id = "";
    public String barcode = "";
    public String title = "";
    public double price = 0;
    public String unit = "";
    public boolean is_in_stock = true;
    public boolean is_in_discount = true;
    public boolean is_support_return = true;
    public List<String> images = new ArrayList<>();
    public String desc = "";

    public boolean parseFromString(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            return parseFromJsonObject(jsonObject);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean parseFromJsonObject(JSONObject jsonObject) {
        try {
            seller_id = jsonObject.getString("seller_id");
            id = jsonObject.getString("id");
            barcode = jsonObject.getString("barcode");
            title = jsonObject.getString("title");
            price = jsonObject.getDouble("price");
            unit = jsonObject.getString("unit");
            is_in_stock = jsonObject.getBoolean("is_in_stock");
            is_in_discount = jsonObject.getBoolean("is_in_discount");
            is_support_return = jsonObject.getBoolean("is_support_return");
            JSONArray array = jsonObject.getJSONArray("images");
            for (int i = 0; i < array.length(); i++) {
                images.add(array.getJSONObject(i).getString("image"));
            }
            desc = jsonObject.getString("desc");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
