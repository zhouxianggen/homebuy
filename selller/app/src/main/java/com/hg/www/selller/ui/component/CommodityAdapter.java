package com.hg.www.selller.ui.component;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Category;
import com.hg.www.selller.service.BasicService;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.service.CommodityService;
import com.hg.www.selller.ui.CommodityListActivity;
import com.hg.www.selller.ui.EditCommodityActivity;

import java.util.ArrayList;
import java.util.List;

public class CommodityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum ITEM_TYPE {
        COMMODITY_CATEGORY,
        COMMODITY
    }
    private static final String TAG = CommodityAdapter.class.getSimpleName();
    public final Context context;
    public final int root;
    public List<Category> categories = new ArrayList<>();
    public List<Commodity> commodities = new ArrayList<>();

    public CommodityAdapter(Context context, int root) {
        this.context = context;
        this.root = root;
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
        if (holder instanceof CategoryViewHolder) {
            Category category = categories.get(position);
            CategoryViewHolder h = (CategoryViewHolder) holder;
            h.mCategory = category;
            h.mTitle.setText(category.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));
        } else if (holder instanceof CommodityViewHolder) {
            Commodity commodity = commodities.get(position - categories.size());
            CommodityViewHolder h = (CommodityViewHolder) holder;
            h.mCommodity = commodity;
            h.mThumbnail.setImageUrl(
                    commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL),
                    VolleyApi.getInstance().getImageLoader()
            );
            h.mTitle.setText(commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE));
            h.mSaleInfo.setText(String.format(
                    "价格：%.2f  周销量：%d  退货量：%d",
                    0.1, 2, 2));

            //h.mSupportReturn.setLayoutParams(new LinearLayout.LayoutParams(h.mSupportReturn.getMeasuredHeight(), h.mSupportReturn.getMeasuredHeight()));
            boolean support_result = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN) != 0;
            h.mSupportReturn.setVisibility(support_result ? View.VISIBLE : View.GONE);
            boolean in_discount = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT) != 0;
            h.mInDiscount.setVisibility(in_discount? View.VISIBLE : View.GONE);
            boolean in_stock = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK) != 0;
            h.mInStock.setVisibility(in_stock? View.VISIBLE : View.GONE);
            h.mOutOfStock.setVisibility(in_stock? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.COMMODITY_CATEGORY.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.commodity_category, parent, false);
            return new CategoryViewHolder(view, context);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.commodity, parent, false);
            return new CommodityViewHolder(view, context);
        }
    }

    public void onUpdateAdapter() {
        categories = CategoryApi.getInstance().getCategories(root);
        commodities = CommodityApi.getInstance().getCommodities(root);
        Log.d(TAG, String.format("get %d categories, %d commodities", categories.size(), commodities.size()));
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context mContext;
        private TextView mTitle;
        private TextView mAmount;
        private Category mCategory;

        public CategoryViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mTitle = (TextView) view.findViewById(R.id.title);
            mAmount = (TextView) view.findViewById(R.id.amount);
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
            popupMenu.inflate(R.menu.menu_set_commodity_category);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_rename:
                            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                            View promptView = layoutInflater.inflate(R.layout.dialog_add_category, null);
                            final EditText editText = (EditText) promptView.findViewById(R.id.input);
                            editText.setText(mCategory.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));

                            new AlertDialogWrapper.Builder(mContext)
                                    .setView(promptView)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String title = editText.getText().toString();
                                            if (!title.isEmpty()) {
                                                mCategory.setStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE, title);
                                                CategoryService.startService(BasicService.ACTION_POST, mCategory.toString());
                                            }
                                        }

                                    })
                                    .show();
                            return true;
                        case R.id.menu_remove:
                            new AlertDialogWrapper.Builder(mContext)
                                    .setMessage(R.string.DIALOG_MESSAGE_DELETE_CATEGORY)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mCategory.setStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_STATUS, "deleted");
                                            CategoryService.startService(BasicService.ACTION_POST, mCategory.toString());
                                        }

                                    })
                                    .show();
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, CommodityListActivity.class);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_ID),
                    String.valueOf(mCategory.getIntProperty(TableSchema.CategoryEntry.COLUMN_NAME_ID)));
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_TITLE),
                    mCategory.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));
            mContext.startActivity(intent);
        }
    }

    public class CommodityViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context mContext;
        private NetworkImageView mThumbnail;
        private TextView mTitle;
        private TextView mSaleInfo;
        private ImageView mSupportReturn;
        private ImageView mInDiscount;
        private ImageView mInStock;
        private ImageView mOutOfStock;
        private Commodity mCommodity;

        public CommodityViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mThumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            mTitle = (TextView) view.findViewById(R.id.title);
            mSaleInfo = (TextView) view.findViewById(R.id.sale_info);
            mSupportReturn = (ImageView) view.findViewById(R.id.support_return);
            mInDiscount = (ImageView) view.findViewById(R.id.in_discount);
            mInStock = (ImageView) view.findViewById(R.id.in_stock);
            mOutOfStock = (ImageView) view.findViewById(R.id.outof_stock);
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
            popupMenu.inflate(R.menu.menu_set_commodity);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_disable:
                            mCommodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, 0);
                            CommodityService.startService(BasicService.ACTION_POST, mCommodity.toString());
                            return true;
                        case R.id.menu_remove:
                            new AlertDialogWrapper.Builder(mContext)
                                    .setMessage(R.string.DIALOG_MESSAGE_DELETE_COMMODITY)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mCommodity.setStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_STATUS, "deleted");
                                            CommodityService.startService(BasicService.ACTION_POST, mCommodity.toString());
                                        }

                                    })
                                    .show();
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, EditCommodityActivity.class);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_ID),
                    String.valueOf(mCommodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_ID)));
            mContext.startActivity(intent);
        }
    }
}