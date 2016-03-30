package com.hg.www.selller.ui;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;

public class ReturnedOrderListActivity extends OrderListActivity {
    private static final String TAG = ReturnedOrderListActivity.class.getSimpleName();

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_RETURNED;
        mTitle = getString(R.string.ACTIVITY_RETURNED_ORDER);
    }
}
