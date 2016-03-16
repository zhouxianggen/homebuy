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
public class Commodity {
    public String id = "";
    public String seller_id = "";
    public String barcode = "";
    public String category_id = "";
    public String title = "";
    public String description = "";
    public String thumbnail = "";
    public String image_1 = "";
    public String image_2 = "";
    public String image_3 = "";
    public String image_4 = "";
    public String image_5 = "";
    public String image_6 = "";
    public String image_7 = "";
    public String image_8 = "";
    public double price = 0;
    public int in_stock = 1;
    public int in_discount = 0;
    public int support_return = 1;
    public int weekly_sales = 0;
    public int monthly_sales = 0;
    public int weekly_returns = 0;
    public int monthly_returns = 0;
    public String category = "";

    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        if (!image_1.isEmpty()) images.add(image_1);
        if (!image_2.isEmpty()) images.add(image_2);
        if (!image_3.isEmpty()) images.add(image_3);
        if (!image_4.isEmpty()) images.add(image_4);
        if (!image_5.isEmpty()) images.add(image_5);
        if (!image_6.isEmpty()) images.add(image_6);
        if (!image_7.isEmpty()) images.add(image_7);
        if (!image_8.isEmpty()) images.add(image_8);
        return images;
    }
}
