package com.hg.www.buyer.ui;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.www.buyer.R;
import com.hg.www.buyer.ui.adapter.SellerAdapter;
import com.hg.www.buyer.ui.event.BusApi;
import com.hg.www.buyer.ui.event.ServicePullDataFinishedEvent;
import com.squareup.otto.Subscribe;

public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    protected Context context;
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeContainer;
    private SellerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        context = getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshContent();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter = new SellerAdapter(context);
        //recyclerView.setAdapter(adapter);
        //adapter.update();
        BusApi.getInstance().register(this);
    }

    @Override
    public void onPause() {
        BusApi.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onUpdateEvent(ServicePullDataFinishedEvent e) {
        Log.d(TAG, String.format("on OrdersUpdatedEvent: [%s][%s]", e.service, e.errors));
        //swipeContainer.setRefreshing(false);
        //adapter.update();
    }

    protected void onRefreshContent() {
        //SellerService.startPull(String.format("area=0"));
    }
}
