package com.hg.www.selller.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hg.www.selller.R;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Order;

public class PrepareOrderListActivity extends OrderListActivity {
    private static final String TAG = PrepareOrderListActivity.class.getSimpleName();
    private String mMessageId = "";
    private String mExpressmanId = "";

    @Override
    protected void init() {
        mOrderStatus = Order.STATUS_PREPARE;
        mTitle = getString(R.string.ACTIVITY_PREPARE_ORDER);

        Intent intent = getIntent();
        mMessageId = intent.getStringExtra(getString(R.string.EXTRA_MESSAGE_ID));
        mExpressmanId = intent.getStringExtra(getString(R.string.EXTRA_LOADING_TIMESTAMP));

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
        mAdapter.orders =  OrderApi.getInstance().getPrepareOrders(mMessageId, mExpressmanId);
        mAdapter.notifyDataSetChanged();
        double summary = 0;
        for (Order order : mAdapter.orders) {
            summary += order.getFloatProperty(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT);
        }
        mSummary.setText(getString(R.string.RMB) + String.valueOf(summary));
    }
}
