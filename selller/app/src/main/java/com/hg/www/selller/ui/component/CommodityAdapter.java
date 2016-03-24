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
import com.hg.www.selller.data.api.CommodityCategoryApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.CommodityCategory;
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
    public final String root;
    public List<CommodityCategory> categories = new ArrayList<>();
    public List<Commodity> commodities = new ArrayList<>();

    public CommodityAdapter(Context context, String root) {
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
        if (holder instanceof CommodityCategoryViewHolder) {
            CommodityCategory category = categories.get(position);
            CommodityCategoryViewHolder h = (CommodityCategoryViewHolder) holder;
            h.mCategory = category;
            h.mTitle.setText(category.title);
            h.mAmount.setText(String.valueOf(category.item_count));
        } else if (holder instanceof CommodityViewHolder) {
            Commodity commodity = commodities.get(position - categories.size());
            CommodityViewHolder h = (CommodityViewHolder) holder;
            h.mCommodity = commodity;
            h.mThumbnail.setImageUrl(
                    commodity.thumbnail, VolleyApi.getInstance().getImageLoader()
            );
            h.mTitle.setText(commodity.title);
            h.mSaleInfo.setText(String.format(
                    "价格：%.2f  周销量：%d  退货量：%d",
                    commodity.price, commodity.weekly_sales, commodity.weekly_returns));

            //h.mSupportReturn.setLayoutParams(new LinearLayout.LayoutParams(h.mSupportReturn.getMeasuredHeight(), h.mSupportReturn.getMeasuredHeight()));
            h.mSupportReturn.setVisibility((commodity.support_return != 0) ? View.VISIBLE : View.GONE);
            h.mInDiscount.setVisibility((commodity.in_discount != 0)? View.VISIBLE : View.GONE);
            h.mInStock.setVisibility((commodity.in_stock != 0)? View.VISIBLE : View.GONE);
            h.mOutOfStock.setVisibility((commodity.in_stock == 0)? View.VISIBLE : View.GONE);
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

    public void onUpdateAdapter() {
        categories = CommodityCategoryApi.getInstance().getCategories(root);
        commodities = CommodityApi.getInstance().getCommodities(root);
        Log.d(TAG, String.format("get %d categories, %d commodities", categories.size(), commodities.size()));
        notifyDataSetChanged();
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
                            editText.setText(mCategory.title);

                            new AlertDialogWrapper.Builder(mContext)
                                    .setView(promptView)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String title = editText.getText().toString();
                                            if (!title.isEmpty()) {
                                                mCategory.title = title;
                                                CommodityService.updateServerCategory(mContext, mCategory, 4,
                                                        new HttpAsyncTask.OnSuccessListener() {
                                                            @Override
                                                            public void onSuccess() {
                                                                CommodityCategoryApi.getInstance().deleteCategory(mCategory);
                                                                CommodityCategoryApi.getInstance().insertCategory(mCategory); // fixme
                                                                onUpdateAdapter();
                                                            }
                                                        }, new HttpAsyncTask.OnFailureListener() {
                                                            @Override
                                                            public void onFailure(String errors) {
                                                                Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
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
                                            CommodityService.updateServerCategory(mContext, mCategory, 4,
                                                    new HttpAsyncTask.OnSuccessListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            CommodityCategoryApi.getInstance().deleteCategory(mCategory); // fixme
                                                            onUpdateAdapter();
                                                        }
                                                    }, new HttpAsyncTask.OnFailureListener() {
                                                        @Override
                                                        public void onFailure(String errors) {
                                                            Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
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
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), mCategory.id);
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_CATEGORY_TITLE), mCategory.title);
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
                            mCommodity.in_stock = 0;
                            CommodityService.updateServerCommodity(mContext, mCommodity, 4,
                                    new HttpAsyncTask.OnSuccessListener() {
                                        @Override
                                        public void onSuccess() {
                                            CommodityApi.getInstance().deleteCommodity(mCommodity);
                                            CommodityApi.getInstance().insertCommodity(mCommodity); // fixme
                                            onUpdateAdapter();
                                        }
                                    }, new HttpAsyncTask.OnFailureListener() {
                                        @Override
                                        public void onFailure(String errors) {
                                            Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            return true;
                        case R.id.menu_remove:
                            new AlertDialogWrapper.Builder(mContext)
                                    .setMessage(R.string.DIALOG_MESSAGE_DELETE_COMMODITY)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            CommodityService.updateServerCommodity(mContext, mCommodity, 4,
                                                    new HttpAsyncTask.OnSuccessListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            CommodityApi.getInstance().deleteCommodity(mCommodity); // fixme
                                                            onUpdateAdapter();
                                                        }
                                                    }, new HttpAsyncTask.OnFailureListener() {
                                                        @Override
                                                        public void onFailure(String errors) {
                                                            Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
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
            intent.putExtra(mContext.getString(R.string.EXTRA_COMMODITY_ID), mCommodity.id);
            mContext.startActivity(intent);
        }
    }
}