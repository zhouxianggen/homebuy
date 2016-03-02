package com.hg.www.selller.data.define;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 商品类目数据格式.
 */
public class CommodityCategory {
    public String id = "";
    public String seller_id = "";
    public String title = "";
    public int item_count = 0;
    public String parent = "";
}
