package com.hg.www.selller.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class CheckReturnOrderListActivity extends AppCompatActivity {
    private static final String TAG = CheckReturnOrderListActivity.class.getSimpleName();
    private String mTitle = "";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private TextView mSummary;
    private String mExpressmanId = "";
    private String mReturnTimestamp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_return_order_list);

        final Context context = this;
        mTitle = getString(R.string.ACTIVITY_CHECK_LOADING_ORDER);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSummary = (TextView) findViewById(R.id.summary);

        Intent intent = getIntent();
        mExpressmanId = intent.getStringExtra(getString(R.string.EXTRA_EXPRESSMAN_ID));
        mReturnTimestamp = intent.getStringExtra(getString(R.string.EXTRA_RETURN_TIMESTAMP));

        Button btn = (Button) findViewById(R.id.btn_action);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Order order : mAdapter.orders) {
                    order.status = Order.STATUS_RETURNED;
                }
                OrderService.updateServerOrders(context, mAdapter.orders, 4,
                        new HttpAsyncTask.OnSuccessListener(){
                            @Override
                            public void onSuccess() {
                                for (Order order : mAdapter.orders) {
                                    OrderApi.getInstance().setOrderStatus(order.id, Order.STATUS_RETURNED);
                                }
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra(getString(R.string.EXTRA_FRAGMENT),
                                        MainActivity.FRAGMENT_ORDER);
                                context.startActivity(intent);
                            }
                        }, new HttpAsyncTask.OnFailureListener() {
                            @Override
                            public void onFailure(String errors) {
                                Toast.makeText(context, errors, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        initializeActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new MyAdapter(this);
            mRecyclerView.setAdapter(mAdapter);
        }
        onUpdateAdapter();
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onUpdateAdapter() {
        Log.d(TAG, "onUpdateAdapter");
        mAdapter.orders =  OrderApi.getInstance().getReturnOrders(mExpressmanId, mReturnTimestamp);
        mAdapter.notifyDataSetChanged();
        double summary = 0;
        for (Order order : mAdapter.orders) {
            summary += order.payment;
        }
        mSummary.setText(getString(R.string.RMB) + String.valueOf(summary));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context = null;
        public List<Order> orders = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Log.d(TAG, "bind view holder " + String.valueOf(position));
            Order order = orders.get(position);
            Commodity commodity = CommodityApi.getInstance().getCommodity(order.commodity_id);
            if (commodity != null) {
                ((OrderViewHolder) holder).mCommodityThumbnail.setImageUrl(
                        commodity.thumbnail, VolleyApi.getInstance().getImageLoader()
                );
                ((OrderViewHolder) holder).mCommodityTitle.setText(commodity.title);
            }
            ((OrderViewHolder) holder).mOrderInfo.setText(
                    String.valueOf(order.amount) + "件 " + String.valueOf(order.payment) + "元");
            ((OrderViewHolder) holder).mAgency.setText(order.agency_id);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.order, parent, false);
            return new OrderViewHolder(view, context);
        }

        public class OrderViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private NetworkImageView mCommodityThumbnail;
            private TextView mCommodityTitle;
            private TextView mOrderInfo;
            private TextView mAgency;
            private TextView mSellerInfo;

            public OrderViewHolder(View view, Context context) {
                super(view);
                mCommodityThumbnail = (NetworkImageView) view.findViewById(R.id.commodity_thumbnail);
                mCommodityTitle = (TextView) view.findViewById(R.id.commodity_title);
                mOrderInfo = (TextView) view.findViewById(R.id.order_info);
                mAgency = (TextView) view.findViewById(R.id.agency);
                mSellerInfo = (TextView) view.findViewById(R.id.seller_info);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
        }
    }
}
