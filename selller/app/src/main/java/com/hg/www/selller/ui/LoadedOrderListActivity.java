package com.hg.www.selller.ui;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;

public class LoadedOrderListActivity extends OrderListActivity {
    private static final String TAG = LoadedOrderListActivity.class.getSimpleName();

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_LOADED;
        mTitle = getString(R.string.ACTIVITY_LOADED_ORDER);
    }
}
