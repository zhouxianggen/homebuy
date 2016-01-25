package com.hg.www.selller.define;

import java.util.ArrayList;
import java.util.List;

public class CommodityGroup {
    static public enum TYPE {
        COMMODITY_CATEGORY,
        COMMODITY_ITEM
    }
    public String id = "";
    public String title = "";
    public int count = 0;
    public TYPE type = TYPE.COMMODITY_ITEM;
    public List<CommodityGroup> subs = new ArrayList<>();
}
