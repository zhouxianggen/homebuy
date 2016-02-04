package com.hg.www.selller.data.define;

/**
 * 商品集合
 */
public class CommodityGroup {
    static public enum TYPE {
        COMMODITY_CATEGORY,
        COMMODITY
    }
    public String id = "";
    public TYPE type = TYPE.COMMODITY;
    public String title = "";
}
