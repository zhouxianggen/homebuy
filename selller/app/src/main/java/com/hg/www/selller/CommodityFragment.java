package com.hg.www.selller;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.www.selller.adapter.CommodityGroupListAdapter;
import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityFragment extends Fragment {
    private static final String TAG = "CommodityFragment";
    private Context mContext;
	private Handler mHandler = new Handler();
	private RecyclerView mRecyclerView;
    private CommodityGroupListAdapter mAdapter;
    private String mRoot = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initializeAdapter();

        return rootView;
    }

    private void initializeAdapter() {
        new Thread(new Runnable() {
            public void run() {
                final List<CommodityGroup> groups = new ArrayList<>();
                final String error = ((MyApplication)mContext.getApplicationContext()).
                        getDataManager().getCommodityGroups(mRoot, groups);
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

}
