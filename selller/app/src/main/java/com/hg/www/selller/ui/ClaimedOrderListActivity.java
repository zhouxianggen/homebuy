package com.hg.www.selller.ui;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;

public class ClaimedOrderListActivity extends OrderListActivity {
    private static final String TAG = ClaimedOrderListActivity.class.getSimpleName();

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_CLAIMED;
        mTitle = getString(R.string.ACTIVITY_CLAIMED_ORDER);
    }
}
