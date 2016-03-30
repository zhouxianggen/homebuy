package com.hg.www.selller.ui;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;

public class AcceptedOrderListActivity extends OrderListActivity {
    private static final String TAG = AcceptedOrderListActivity.class.getSimpleName();

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_ACCEPTED;
        mTitle = getString(R.string.ACTIVITY_ACCEPTED_ORDER);
    }
}
