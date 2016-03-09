package com.hg.www.selller.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Expressman;
import com.hg.www.selller.data.define.ExpressmanMessage;
import com.hg.www.selller.ui.CommodityFragment;
import com.hg.www.selller.ui.OrderFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String FRAGMENT_ORDER = "fragment_order";
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.drawer_item_1;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigate(mNavItemId);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String fragment = intent.getStringExtra(getString(R.string.EXTRA_FRAGMENT));
        if (fragment != null) {
            if (fragment.equals(FRAGMENT_ORDER)) {
                viewPager.setCurrentItem(1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void navigate(final int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        } else if (id == R.id.action_scan) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, intent.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();
                ExpressmanMessage message = new ExpressmanMessage();
                if (message.parseFromString(intent.getStringExtra("SCAN_RESULT"))) {
                    if (message.type == ExpressmanMessage.CHECK_LOADING_ORDER) {
                        Intent newIntent = new Intent(this, CheckLoadingOrderListActivity.class);
                        newIntent.putExtra(getString(R.string.EXTRA_EXPRESSMAN_ID), message.expressman_id);
                        newIntent.putExtra(getString(R.string.EXTRA_LOADING_TIMESTAMP), message.timestamp);
                        startActivity(newIntent);
                    } else if (message.type == ExpressmanMessage.CHECK_RETURN_ORDER) {
                        Intent newIntent = new Intent(this, CheckReturnOrderListActivity.class);
                        newIntent.putExtra(getString(R.string.EXTRA_EXPRESSMAN_ID), message.expressman_id);
                        newIntent.putExtra(getString(R.string.EXTRA_RETURN_TIMESTAMP), message.timestamp);
                        startActivity(newIntent);
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    public static class TabsPagerAdapter extends FragmentPagerAdapter {
        private Context context;

        public TabsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new CommodityFragment();
                case 1:
                    return new OrderFragment();
                case 2:
                    return new StatisticFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return context.getString(R.string.tab_commodity);
                case 1:
                    return context.getString(R.string.tab_order);
                case 2:
                    return context.getString(R.string.tab_statistic);
            }
            return null;
        }
    }

    public static class StatisticFragment extends  Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_deal, container, false);
            return rootView;
        }
    }
}
