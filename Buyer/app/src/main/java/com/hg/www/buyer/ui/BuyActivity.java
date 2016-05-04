package com.hg.www.buyer.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hg.www.buyer.R;
import com.hg.www.buyer.data.api.CommodityApi;
import com.hg.www.buyer.service.CommodityService;
import com.hg.www.buyer.ui.adapter.SmartFragmentStatePagerAdapter;
import com.hg.www.buyer.ui.event.BusApi;
import com.hg.www.buyer.ui.event.ServicePullDataFinishedEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity {
    private static final String TAG = BuyActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabsFragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private int sellerId;
    private String sellerTitle;
    private Context context;
    private FloatingActionButton cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        context = this;
        Intent intent = getIntent();
        sellerId = intent.getIntExtra(getString(R.string.extra_seller_id), -1);
        sellerTitle = intent.getStringExtra(getString(R.string.extra_seller_title));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(sellerTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TabsFragmentPagerAdapter(getSupportFragmentManager(),
                BuyActivity.this, sellerId);
        //viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        BusApi.getInstance().register(this);
        String args = String.format("seller_id=%d", sellerId);
        CommodityService.startPull(args);
    }

    @Override
    public void onPause() {
        BusApi.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    @Subscribe
    public void onUpdateEvent(ServicePullDataFinishedEvent e) {
        Log.d(TAG, String.format("onUpdateEvent: [%s][%s]", e.service, e.errors));
        adapter.categories = CommodityApi.getInstance().getSellerCategories(sellerId);
        for (String c : adapter.categories) {
            Log.d(TAG, String.format("category [%s]", c));
        }
        //for (int i = 0; i < 10; i++)  adapter.categories.add(String.format("TAB%d", i));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
    }

    public static class TabsFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
        private Context context;
        private int sellerId;
        public List<String> categories = new ArrayList<>();

        public TabsFragmentPagerAdapter(FragmentManager fm, Context context, int sellerId) {
            super(fm);
            this.context = context;
            this.sellerId = sellerId;
        }

        @Override
        public Fragment getItem(int i) {
            Log.d(TAG, String.format("getItem [%d][%d]", i, categories.size()));
            Fragment fragment = new CommodityFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", categories.get(i));
            bundle.putInt("seller_id", sellerId);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            Log.d(TAG, String.format("getCount [%d]", categories.size()));
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.d(TAG, String.format("getPageTitle [%d]", position));
            return categories.get(position);
        }

        @Override
        public int getItemPosition(Object item) {
            CommodityFragment fragment = (CommodityFragment)item;
            String category = fragment.getCategory();
            int position = categories.indexOf(category);
            Log.d(TAG, String.format("getItemPosition [%s], position is [%d]", category, position));

            if (position >= 0) {
                return position;
            } else {
                return POSITION_NONE;
            }
        }
    }
}
