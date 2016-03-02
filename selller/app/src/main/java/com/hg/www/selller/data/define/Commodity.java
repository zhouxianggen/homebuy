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
    public int in_stock = 0;
    public int in_discount = 0;
    public int support_return = 1;
    public int weekly_sales = 0;
    public int monthly_sales = 0;
    public int weekly_returns = 0;
    public int monthly_returns = 0;
    public String category = "";
}
