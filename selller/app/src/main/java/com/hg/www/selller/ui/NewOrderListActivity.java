package com.hg.www.selller.ui;

import android.view.View;
import android.widget.Button;
import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;

public class NewOrderListActivity extends OrderListActivity {
    private static final String TAG = NewOrderListActivity.class.getSimpleName();

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_NEW;
        mTitle = getString(R.string.ACTIVITY_NEW_ORDER);

        Button btn = (Button) findViewById(R.id.btn_action);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
