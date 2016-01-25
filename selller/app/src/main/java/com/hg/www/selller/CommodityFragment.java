package com.hg.www.selller;

import android.content.Context;
import android.os.Bundle;
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

import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getContext());
        mAdapter.onUpdate();
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private enum ITEM_TYPE {
            COMMODITY_CATEGORY,
            COMMODITY_ITEM
        }

        private final Context mContext;
        private List<CommodityGroup> mCommodityGroups  = new ArrayList<>();;


        public RecyclerViewAdapter(Context context) {
            mContext = context;
        }

        public void onUpdate() {
            ((MyApplication)mContext.getApplicationContext()).getDataManager().
                    getCommodityGroups(mCommodityGroups);
        }

        @Override
        public int getItemCount() {
            return mCommodityGroups.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mCommodityGroups.get(position).type == CommodityGroup.TYPE.COMMODITY_CATEGORY) {
                return ITEM_TYPE.COMMODITY_CATEGORY.ordinal();
            } else {
                return ITEM_TYPE.COMMODITY_ITEM.ordinal();
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CommodityGroup group = mCommodityGroups.get(position);
            if (holder instanceof CommodityCategoryViewHolder) {
                ((CommodityCategoryViewHolder) holder).mTitle.setText(group.title);
            } else if (holder instanceof CommodityItemViewHolder) {
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.COMMODITY_CATEGORY.ordinal()) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.commodity_category, parent, false);
                return new CommodityCategoryViewHolder(view, mContext);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.commodity_item, parent, false);
                return new CommodityItemViewHolder(view, mContext);
            }
        }

        public static class CommodityCategoryViewHolder extends RecyclerView.ViewHolder {
            private Context mContext;
            private TextView mTitle;
            private TextView mCount;

            public CommodityCategoryViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mTitle = (TextView) view.findViewById(R.id.title);
                mCount = (TextView) view.findViewById(R.id.count);
                ImageView imageView = (ImageView) view.findViewById(R.id.popup_menu);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                showPopupMenu(view);
                            }
                        });
                    }
                });
            }

            private void showPopupMenu(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.inflate(R.menu.popup_commodity_category_item);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_rename:
                                return true;
                            case R.id.menu_remove:
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        }

        public static class CommodityItemViewHolder extends RecyclerView.ViewHolder {
            private Context mContext;

            public CommodityItemViewHolder(View view, Context context) {
                super(view);
                mContext = context;
            }
        }
    }
}
