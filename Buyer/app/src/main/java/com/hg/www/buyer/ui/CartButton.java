package com.hg.www.buyer.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Commodity;
import com.hg.www.buyer.data.define.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartButton {
    private static CartButton instance;
    private Context mContext;
    private FloatingActionButton mButton;
    private Map<Integer, Integer> mCommodities;

    private CartButton() {
        mCommodities = new HashMap<>();
    }

    public static synchronized CartButton getInstance() {
        if (instance == null) {
            instance = new CartButton();
        }
        return instance;
    }

    public void init(Context context, FloatingActionButton button) {
        if (instance != null) {
            instance.mContext = context;
            instance.mButton = button;
        }
    }

    public int addCommodity(Commodity commodity) {
        int id = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_ID);
        int count = 1;
        if (mCommodities.containsKey(id)) {
            count = mCommodities.get(id) + 1;
        }
        mCommodities.put(id, count);
        return count;
    }
}
