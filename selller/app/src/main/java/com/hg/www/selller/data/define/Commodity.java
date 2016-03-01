package com.hg.www.selller.data.define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品数据格式
 */
public class Commodity implements JsonSerializable {
    public String id = "";
    public String seller_id = "";
    public String barcode = "";
    public String category_id = "";
    public String title = "";
    public String description = "";
    public String thumbnail = "";
    public List<String> images = new ArrayList<>();
    public double price = 0;
    public String unit = "";
    public int in_stock = 0;
    public int in_discount = 0;
    public int support_return = 1;
    public int weekly_sales = 0;
    public int monthly_sales = 0;
    public int yearly_sales = 0;
    public int weekly_returns = 0;
    public int monthly_returns = 0;
    public int yearly_returns = 0;

    @Override
    public Object parse(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            seller_id = jsonObject.getString("seller_id");
            barcode = jsonObject.getString("barcode");
            category_id = jsonObject.getString("category_id");
            title = jsonObject.getString("title");
            description = jsonObject.getString("description");
            thumbnail = jsonObject.getString("thumbnail");
            JSONArray array = jsonObject.getJSONArray("images");
            for (int i = 0; i < array.length(); i++) {
                images.add(array.getString(i));
            }
            price = jsonObject.getDouble("price");
            unit = jsonObject.getString("unit");
            in_stock = jsonObject.getInt("in_stock");
            in_discount = jsonObject.getBoolean("in_discount");
            support_return = jsonObject.getBoolean("support_return");
            weekly_sales = jsonObject.getInt("weekly_sales");
            monthly_sales = jsonObject.getInt("monthly_sales");
            yearly_sales = jsonObject.getInt("yearly_sales");
            weekly_returns = jsonObject.getInt("weekly_returns");
            monthly_returns = jsonObject.getInt("monthly_returns");
            yearly_returns = jsonObject.getInt("yearly_returns");
        } catch (JSONException e) {
            return null;
        }
        return this;
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
