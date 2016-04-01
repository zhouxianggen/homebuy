package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.hg.scanner.barcode.decoding.DecodeFormatManager;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.service.CommodityService;
import com.hg.www.selller.ui.component.AddCommodityButton;
import com.hg.www.selller.ui.component.CommodityAdapter;

public class CommodityListActivity extends AppCompatActivity {
    private static final String TAG = CommodityListActivity.class.getSimpleName();
    private Context mContext;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private CommodityAdapter mAdapter;
    private int mCategoryId;
    private String mCategoryTitle;
    private AddCommodityButton addCommodityButton;

    private class RefreshTask extends AsyncTask<Void, Integer, String> {

        protected String doInBackground(Void... params) {
            return new CommodityService().get();
        }

        protected void onPostExecute(String errors) {
            swipeContainer.setRefreshing(false);
            if (errors.isEmpty()) {
                onRefresh();
            } else {
                Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_list);

        mContext = this;

        Intent intent = getIntent();
        mCategoryId = intent.getIntExtra(getString(R.string.EXTRA_CATEGORY_ID), -1);
        mCategoryTitle = intent.getStringExtra(getString(R.string.EXTRA_CATEGORY_TITLE));
        Log.d(TAG, String.format("start commodity list activity with category [%d]", mCategoryId));
        if (mCategoryId == -1 || mCategoryTitle == null) {
            finish();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommodityAdapter(mContext, mCategoryId);
        mRecyclerView.setAdapter(mAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshTask().execute();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        addCommodityButton = new AddCommodityButton(this, findViewById(android.R.id.content), mCategoryId);
        initializeActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        onRefresh();
    }

    void onRefresh() {
        Log.d(TAG, "onRefresh");
        mAdapter.clear();
        mAdapter.update();
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(mCategoryTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String barcode = intent.getStringExtra(getString(R.string.EXTRA_SCAN_RESULT));
                BarcodeFormat format = BarcodeFormat.values()[intent.getIntExtra(getString(R.string.EXTRA_BARCODE_FORMAT), 0)];
                Toast.makeText(this, barcode, Toast.LENGTH_SHORT).show();

                if (DecodeFormatManager.PRODUCT_FORMATS.contains(format)) {
                    Intent newIntent = new Intent(this, EditCommodityActivity.class);
                    newIntent.putExtra(getString(R.string.EXTRA_CATEGORY_ID), mCategoryId);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID), barcode);
                    startActivity(newIntent);
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
