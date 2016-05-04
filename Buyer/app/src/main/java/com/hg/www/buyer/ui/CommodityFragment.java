package com.hg.www.buyer.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hg.www.buyer.R;
import com.hg.www.buyer.data.api.CommodityApi;
import com.hg.www.buyer.ui.adapter.CommodityGridViewAdapter;

public class CommodityFragment extends Fragment {
    private static final String TAG = CommodityFragment.class.getSimpleName();
    private String category;
    private int sellerId;
    protected Context context;
    protected GridView gridView;
    protected CommodityGridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        Bundle bundle = getArguments();
        category = bundle.getString("category");
        sellerId = bundle.getInt("seller_id");
        Log.d(TAG, String.format("onCreateView with category: [%s]", category));

        context = getActivity();
        gridView = (GridView) rootView.findViewById(R.id.grid_view);
        adapter = new CommodityGridViewAdapter(context);
        adapter.commodities = CommodityApi.getInstance().getSellerCategoryCommodities(sellerId, category);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(2);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // go to detail page
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String getCategory() {
        return category;
    }
}
