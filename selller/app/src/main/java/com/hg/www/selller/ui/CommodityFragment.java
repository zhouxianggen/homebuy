package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.hg.scanner.barcode.decoding.DecodeFormatManager;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.ui.component.AddCommodityButton;
import com.hg.www.selller.ui.component.CommodityAdapter;

public class CommodityFragment extends Fragment {
    private static final String TAG = CommodityFragment.class.getSimpleName();
    private static final int parent = 0;
    private Context mContext;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private CommodityAdapter mAdapter;
    private AddCommodityButton addCommodityButton;

    private class RefreshTask extends AsyncTask<Void, Integer, String> {

        protected String doInBackground(Void... params) {
            return new CategoryService().get();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommodityAdapter(mContext, parent);
        mRecyclerView.setAdapter(mAdapter);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
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

        addCommodityButton = new AddCommodityButton(this, rootView, parent);

        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String barcode = intent.getStringExtra(getString(R.string.EXTRA_SCAN_RESULT));
                BarcodeFormat format = BarcodeFormat.values()[intent.getIntExtra(getString(R.string.EXTRA_BARCODE_FORMAT), 0)];
                Toast.makeText(mContext, barcode, Toast.LENGTH_SHORT).show();

                if (DecodeFormatManager.PRODUCT_FORMATS.contains(format)) {
                    Intent newIntent = new Intent(mContext, EditCommodityActivity.class);
                    newIntent.putExtra(getString(R.string.EXTRA_CATEGORY_ID), parent);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID), barcode);
                    mContext.startActivity(newIntent);
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
