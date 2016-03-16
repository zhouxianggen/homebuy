package com.hg.www.selller.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.CommodityCategoryApi;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.CommodityCategory;

import java.util.ArrayList;
import java.util.List;

public class CommodityFragment extends Fragment {
    private static final String TAG = CommodityFragment.class.getSimpleName();
    private Context mContext;
	private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        final FloatingActionButton removeAction = (FloatingActionButton) rootView.findViewById(R.id.action_add_commodity);
        removeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCommodityActivity.class);
                mContext.startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new MyAdapter(mContext);
            mRecyclerView.setAdapter(mAdapter);
        }
        onUpdateAdapter();
        super.onResume();
    }

    public void onUpdateAdapter() {
        mAdapter.categories = CommodityCategoryApi.getInstance().getCategories("root");
        mAdapter.commodities = CommodityApi.getInstance().getCommodities("root");
        Log.d(TAG, "onUpdateAdapter " + String.valueOf(mAdapter.categories.size()));
        Log.d(TAG, "onUpdateAdapter " + String.valueOf(mAdapter.commodities.size()));
        mAdapter.notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        COMMODITY_CATEGORY,
        COMMODITY
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final Context context;
        public List<CommodityCategory> categories = new ArrayList<>();
        public List<Commodity> commodities = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return categories.size() + commodities.size();
        }

        @Override
        public int getItemViewType(int position) {
            Log.d(TAG, "getItemViewType " + String.valueOf(position));
            if (position < categories.size()) {
                return ITEM_TYPE.COMMODITY_CATEGORY.ordinal();
            } else {
                return ITEM_TYPE.COMMODITY.ordinal();
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder " + String.valueOf(position));
            if (holder instanceof CommodityCategoryViewHolder) {
                CommodityCategory category = categories.get(position);
                ((CommodityCategoryViewHolder) holder).mCategory = category;
                ((CommodityCategoryViewHolder) holder).mTitle.setText(category.title);
                ((CommodityCategoryViewHolder) holder).mAmount.setText(String.valueOf(category.item_count));
            } else if (holder instanceof CommodityViewHolder) {
                Commodity commodity = commodities.get(position - categories.size());
                ((CommodityViewHolder) holder).mCommodity = commodity;
                //((CommodityViewHolder) holder).mThumbnail.setText(orderPackage.getTitle());
                ((CommodityViewHolder) holder).mTitle.setText(commodity.title);
                ((CommodityViewHolder) holder).mPrice.setText(String.valueOf(commodity.price));
                ((CommodityViewHolder) holder).mWeeklySales.setText(String.valueOf(commodity.weekly_sales));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.COMMODITY_CATEGORY.ordinal()) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.commodity_category, parent, false);
                return new CommodityCategoryViewHolder(view, context);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.commodity, parent, false);
                return new CommodityViewHolder(view, context);
            }
        }

        public class CommodityCategoryViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private Context mContext;
            private TextView mTitle;
            private TextView mAmount;
            private CommodityCategory mCategory;

            public CommodityCategoryViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mTitle = (TextView) view.findViewById(R.id.title);
                mAmount = (TextView) view.findViewById(R.id.amount);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommodityListActivity.class);
                intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), mCategory.id);
                intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_TITLE), mCategory.title);
                mContext.startActivity(intent);
            }
        }

        public class CommodityViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private Context mContext;
            private ImageView mThumbnail;
            private TextView mTitle;
            private TextView mPrice;
            private TextView mWeeklySales;
            private Commodity mCommodity;

            public CommodityViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                mTitle = (TextView) view.findViewById(R.id.title);
                mPrice = (TextView) view.findViewById(R.id.price);
                mWeeklySales = (TextView) view.findViewById(R.id.sale_volume);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCommodityActivity.class);
                intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_ITEM_ID), mCommodity.id);
                mContext.startActivity(intent);
            }
        }
    }

}
