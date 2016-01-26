package com.hg.www.selller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NavUtils;
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

import com.hg.www.selller.adapter.CommodityGroupListAdapter;
import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityGroupListActivity extends AppCompatActivity {
    private static final String TAG = "CommodityGroupList";
    private Context mContext;
    private Handler mHandler = new Handler();
	private RecyclerView mRecyclerView;
    private CommodityGroupListAdapter mAdapter;
    private String mRoot;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_group_list);

        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        mRoot = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_GROUP_ROOT));
        mTitle = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_GROUP_TITLE));

        initializeActionBar();
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
                final List<CommodityGroup> groups = new ArrayList<>();
                final String error = ((MyApplication)getApplicationContext()).getDataManager().
                        getCommodityGroups(mRoot, groups);
                mHandler.post(new Runnable() {
                    public void run() {
                        if (error.isEmpty()) {
                            mAdapter = new CommodityGroupListAdapter(mContext, mRoot);
                            mAdapter.groups = groups;
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
        //switch (item.getItemId()) {
            //case android.R.id.home:
                //Intent upIntent = NavUtils.getParentActivityIntent(this);
                //NavUtils.navigateUpTo(this, upIntent);
                //return true;
        //}
        //return super.onOptionsItemSelected(item);
    }
}
