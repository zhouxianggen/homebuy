package com.hg.www.selller.ui;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.ui.component.OrderAdapter;

public class OrderListActivity extends AppCompatActivity {
    protected static final String TAG = NewOrderListActivity.class.getSimpleName();
    protected String mTitle = "";
    protected RecyclerView mRecyclerView;
    protected OrderAdapter mAdapter;
    protected String mOrderStatus = "";
    protected TextView mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSummary = (TextView) findViewById(R.id.summary);

        init();
        initializeActionBar();
    }

    protected void init() {
        mOrderStatus = Order.STATUS_NONE;
        mTitle = getString(R.string.ACTIVITY_ORDER_LIST);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new OrderAdapter(this, mOrderStatus);
            mRecyclerView.setAdapter(mAdapter);
        }
        onUpdateAdapter();
    }

    public void onUpdateAdapter() {
        mAdapter.onUpdate();
        mSummary.setText(getString(R.string.RMB) + String.valueOf(mAdapter.getTotalPayment()));
    }

    protected void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }
}
