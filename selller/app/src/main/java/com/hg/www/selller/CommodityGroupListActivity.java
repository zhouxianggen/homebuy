package com.hg.www.selller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hg.www.selller.adapter.CommodityGroupListAdapter;
import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityGroupListActivity extends AppCompatActivity {
    private static final String TAG = "CommodityGroupListActivity";
    private Handler mHandler = new Handler();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommodityGroupListAdapter mAdapter;
    private String mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_group_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent intent = getIntent();
        mRoot = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_GROUP_ID));
        mAdapter = new CommodityGroupListAdapter(this,
                intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_GROUP_ID)));
        mAdapter.onUpdate();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            initializeAdapter();
        }
    }

    private void initializeAdapter() {
        new Thread(new Runnable() {
            public void run() {
                final Context context = getApplicationContext();
                final List<CommodityGroup> groups = new ArrayList<>();
                final String error = ((MyApplication)context).getDataManager().
                        getCommodityGroups(mRoot, groups);
                mHandler.post(new Runnable() {
                    public void run() {
                        if (error.isEmpty()) {
                            mAdapter = new CommodityGroupListAdapter(this, mRoot);
                            mListAdapter.linkers = linkers;
                            mListView.setAdapter(mListAdapter);
                        } else {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}
