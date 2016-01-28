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

import com.hg.www.selller.CommodityGroupListActivity;
import com.hg.www.selller.EditCommodityActivity;
import com.hg.www.selller.R;
import com.hg.www.selller.define.CommodityGroup;

import java.util.ArrayList;
import java.util.List;

public class CommodityGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private enum ITEM_TYPE {
        COMMODITY_CATEGORY,
        COMMODITY_ITEM
    }

    public final Context context;
    public final String root;
    public List<CommodityGroup> groups = new ArrayList<>();


    public CommodityGroupListAdapter(Context context, String root) {
        this.context = context;
        this.root = root;
    }

    public void onUpdate() {
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (groups.get(position).type == CommodityGroup.TYPE.COMMODITY_CATEGORY) {
            return ITEM_TYPE.COMMODITY_CATEGORY.ordinal();
        } else {
            return ITEM_TYPE.COMMODITY_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommodityGroup group = groups.get(position);
        if (holder instanceof CommodityCategoryViewHolder) {
            ((CommodityCategoryViewHolder) holder).mGroup = group;
            ((CommodityCategoryViewHolder) holder).mTitle.setText(group.title);
            ((CommodityCategoryViewHolder) holder).mCount.setText(String.valueOf(group.count));
        } else if (holder instanceof CommodityItemViewHolder) {
            ((CommodityItemViewHolder) holder).mGroup = group;
            ((CommodityItemViewHolder) holder).mTitle.setText(group.title);
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
                    R.layout.commodity_item, parent, false);
            return new CommodityItemViewHolder(view, context);
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
            view.setOnClickListener(this);
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
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_GROUP_ROOT), mGroup.id);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_GROUP_TITLE), mGroup.title);
            mContext.startActivity(intent);
        }
    }

    public static class CommodityItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context mContext;
        private TextView mTitle;
        private CommodityGroup mGroup;

        public CommodityItemViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mTitle = (TextView) view.findViewById(R.id.commodity_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, EditCommodityActivity.class);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_ITEM_ID), mGroup.id);
            mContext.startActivity(intent);
        }
    }
}
