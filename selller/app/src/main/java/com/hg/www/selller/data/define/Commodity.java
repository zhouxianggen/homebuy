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
    public String title = "";
    public String description = "";
    public String thumbnail = "http://img02.tooopen.com/downs/images/2010/7/29/sy_20100729212642929021.jpg";
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
        if (image_1 != null && !image_1.isEmpty()) images.add(image_1);
        if (image_2 != null && !image_2.isEmpty()) images.add(image_2);
        if (image_3 != null && !image_3.isEmpty()) images.add(image_3);
        if (image_4 != null && !image_4.isEmpty()) images.add(image_4);
        if (image_5 != null && !image_5.isEmpty()) images.add(image_5);
        if (image_6 != null && !image_6.isEmpty()) images.add(image_6);
        if (image_7 != null && !image_7.isEmpty()) images.add(image_7);
        if (image_8 != null && !image_8.isEmpty()) images.add(image_8);
        return images;
    }

    public void addImage(String image) {
        if (image_1 == null || image_1.isEmpty()) image_1 = image;
        else if (image_2 == null || image_2.isEmpty()) image_2 = image;
        else if (image_3 == null || image_3.isEmpty()) image_3 = image;
        else if (image_4 == null || image_4.isEmpty()) image_4 = image;
        else if (image_5 == null || image_5.isEmpty()) image_5 = image;
        else if (image_6 == null || image_6.isEmpty()) image_6 = image;
        else if (image_7 == null || image_7.isEmpty()) image_7 = image;
        else if (image_8 == null || image_8.isEmpty()) image_8 = image;
    }

    public void setThumbnail(String image) {
        thumbnail = image;
    }

    public void deleteImage(String image) {
        List<String> images = getImages();
        int pos = images.indexOf(image);
        if (pos >= 0) {
            images.remove(pos);
            image_1 = images.size() > 0? images.get(0) : "";
            image_2 = images.size() > 1? images.get(1) : "";
            image_3 = images.size() > 2? images.get(2) : "";
            image_4 = images.size() > 3? images.get(3) : "";
            image_5 = images.size() > 4? images.get(4) : "";
            image_6 = images.size() > 5? images.get(5) : "";
            image_7 = images.size() > 6? images.get(6) : "";
            image_8 = images.size() > 7? images.get(7) : "";
        }
    }
}
