package com.hg.www.selller.data;

import android.content.SharedPreferences;

import com.hg.www.selller.MyApplication;
import com.hg.www.selller.data.define.CommodityGroup;
import com.hg.www.selller.data.define.Commodity;

import java.util.List;

public class DataManager {
    private static final String COMMODITY_PREFERENCES = "COMMODITY_PREFERENCES";
    protected MyApplication mContext;

    public DataManager(MyApplication context) {
        mContext = context;
    }

    public Commodity GetCommodity(String id) {
        Commodity commodityItem = new Commodity();
        if (!id.isEmpty()) {
            SharedPreferences preferences = mContext.getSharedPreferences(COMMODITY_PREFERENCES, mContext.MODE_PRIVATE);
            String str = preferences.getString(id, "");
            if (!str.isEmpty()) {
                commodityItem.parseFromString(preferences.getString(id, ""));
            }
        }
        return commodityItem;
    }

    public CommodityGroup getCommodityGroup(String id) {
        CommodityGroup group = new CommodityGroup();
        group.id = "1";
        group.title = "CATEGORY 1";
        group.type = CommodityGroup.TYPE.COMMODITY_CATEGORY;
        return group;
    }

    public String getCommodityGroups(String root, List<CommodityGroup> groups) {
        groups.clear();
        if (root.equals("")) {
            CommodityGroup group = new CommodityGroup();
            group.id = "1";
            group.title = "洗涤用品";
            group.type = CommodityGroup.TYPE.COMMODITY_CATEGORY;
            groups.add(group);

            CommodityGroup group2 = new CommodityGroup();
            group2.id = "2";
            group2.title = "纸类";
            group2.type = CommodityGroup.TYPE.COMMODITY_CATEGORY;
            groups.add(group2);
        } else if (root.equals("2")) {
            CommodityGroup group = new CommodityGroup();
            group.id = "21";
            group.title = "清风抽纸4盒装";
            group.type = CommodityGroup.TYPE.COMMODITY_ITEM;
            groups.add(group);

            CommodityGroup group2 = new CommodityGroup();
            group2.id = "22";
            group2.title = "心相印卷纸10包装";
            group2.type = CommodityGroup.TYPE.COMMODITY_ITEM;
            groups.add(group2);
        } else if (root.equals("1")) {
            CommodityGroup group = new CommodityGroup();
            group.id = "11";
            group.title = "洗洁精";
            group.type = CommodityGroup.TYPE.COMMODITY_CATEGORY;
            groups.add(group);

            CommodityGroup group2 = new CommodityGroup();
            group2.id = "12";
            group2.title = "雕牌透明皂";
            group2.type = CommodityGroup.TYPE.COMMODITY_ITEM;
            groups.add(group2);
        } else if (root.equals("11")) {
            CommodityGroup group = new CommodityGroup();
            group.id = "111";
            group.title = "立白洗洁精";
            group.type = CommodityGroup.TYPE.COMMODITY_ITEM;
            groups.add(group);

            CommodityGroup group2 = new CommodityGroup();
            group2.id = "112";
            group2.title = "白猫洗洁精";
            group2.type = CommodityGroup.TYPE.COMMODITY_ITEM;
            groups.add(group2);
        }
        return "";
    }
}
