package com.hg.www.selller.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hg.www.selller.R;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Order;

public class CheckReturnOrderListActivity extends OrderListActivity {
    private static final String TAG = CheckReturnOrderListActivity.class.getSimpleName();
    private String mExpressmanId = "";
    private String mCheckReturnTimestamp = "";

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_CHECK_RETURN;
        mTitle = getString(R.string.ACTIVITY_CHECK_RETURN_ORDER);

        Intent intent = getIntent();
        mExpressmanId = intent.getStringExtra(getString(R.string.EXTRA_EXPRESSMAN_ID));
        mCheckReturnTimestamp = intent.getStringExtra(getString(R.string.EXTRA_RETURN_TIMESTAMP));

        Button btn = (Button) findViewById(R.id.btn_action);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onUpdateAdapter() {
        Log.d(TAG, "onUpdateAdapter");
        mAdapter.orders =  OrderApi.getInstance().getCheckReturnOrders(mExpressmanId, mCheckReturnTimestamp);
        mAdapter.notifyDataSetChanged();
        double summary = 0;
        for (Order order : mAdapter.orders) {
            summary += order.getFloatProperty(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT);
        }
        mSummary.setText(getString(R.string.RMB) + String.valueOf(summary));
    }
}
