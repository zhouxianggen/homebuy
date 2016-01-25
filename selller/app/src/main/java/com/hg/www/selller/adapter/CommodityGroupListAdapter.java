package com.hg.www.selller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.www.selller.CommodityGroupListActivity;
import com.hg.www.selller.MyApplication;
import com.hg.www.selller.R;
import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private enum ITEM_TYPE {
        COMMODITY_CATEGORY,
        COMMODITY_ITEM
    }

    private final Context mContext;
    private final String mRoot;
    private List<CommodityGroup> mGroups  = new ArrayList<>();;


    public CommodityGroupListAdapter(Context context, String root) {
        mContext = context;
        mRoot = root;
    }

    public void onUpdate() {
        new Thread(new Runnable() {
            public void run() {
                final Context context = mContext.getApplicationContext();
                String error = ((MyApplication)context).getDataManager().
                        getCommodityGroups(mRoot, mGroups);
                if (!error.isEmpty()) {
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mGroups.get(position).type == CommodityGroup.TYPE.COMMODITY_CATEGORY) {
            return ITEM_TYPE.COMMODITY_CATEGORY.ordinal();
        } else {
            return ITEM_TYPE.COMMODITY_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommodityGroup group = mGroups.get(position);
        if (holder instanceof CommodityCategoryViewHolder) {
            ((CommodityCategoryViewHolder) holder).mGroup = group;
            ((CommodityCategoryViewHolder) holder).mTitle.setText(group.title);
            ((CommodityCategoryViewHolder) holder).mCount.setText(String.valueOf(group.count));
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

    public static class CommodityCategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context mContext;
        private TextView mTitle;
        private TextView mCount;
        private CommodityGroup mGroup;

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

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, CommodityGroupListActivity.class);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_GROUP_ID), mGroup.id);
            mContext.startActivity(intent);
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
