package com.hg.www.selller.data;

import android.content.SharedPreferences;

import com.hg.www.selller.MyApplication;
import com.hg.www.selller.define.CommodityGroup;
import com.hg.www.selller.define.CommodityItem;

import java.util.List;

public class DataManager {
    private static final String COMMODITY_PREFERENCES = "COMMODITY_PREFERENCES";
    protected MyApplication mContext;

    public DataManager(MyApplication context) {
        mContext = context;
    }

    public CommodityItem GetCommodity(String id) {
        CommodityItem commodityItem = new CommodityItem();
        if (!id.isEmpty()) {
            SharedPreferences preferences = mContext.getSharedPreferences(COMMODITY_PREFERENCES, mContext.MODE_PRIVATE);
            String str = preferences.getString(id, "");
            if (!str.isEmpty()) {
                commodityItem.parseFromString(preferences.getString(id, ""));
            }
        }
        return commodityItem;
    }

    public boolean GetCommodityGroups(List<CommodityGroup> groups) {
        CommodityGroup group = new CommodityGroup();
        group.id = "1";
        group.title = "CATEGORY 1";
        group.type = CommodityGroup.TYPE.COMMODITY_CATEGORY;
        groups.add(group);
        groups.add(group);
        groups.add(group);
        groups.add(group);
        groups.add(group);
        groups.add(group);
        groups.add(group);
        return true;
    }
}
