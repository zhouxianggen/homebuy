package com.hg.www.buyer.ui;

import android.util.Log;

import com.hg.www.buyer.service.BasicService;
import com.hg.www.buyer.service.SellerService;
import com.hg.www.buyer.ui.adapter.SellerAdapter;
import com.hg.www.buyer.ui.event.BusApi;
import com.hg.www.buyer.ui.event.ServicePullDataFinishedEvent;
import com.squareup.otto.Subscribe;

public class SellerFragment extends PageFragment {
    private static final String TAG = SellerFragment.class.getSimpleName();
    private SellerAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
        BusApi.getInstance().register(this);
    }

    @Override
    public void onPause() {
        BusApi.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onUpdateEvent(ServicePullDataFinishedEvent e) {
        Log.d(TAG, String.format("on SellersUpdatedEvent: [%s][%s]", e.service, e.errors));
        swipeContainer.setRefreshing(false);
        adapter.update();
    }

    @Override
    protected void initAdapter() {
        adapter = new SellerAdapter(context);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRefreshContent() {
        SellerService.startPull(String.format("area=0"));
    }
}
