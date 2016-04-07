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
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Category;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.service.CommodityService;
import com.hg.www.selller.service.HttpAsyncTask;
import com.hg.www.selller.ui.CommodityListActivity;
import com.hg.www.selller.ui.EditCommodityActivity;

import java.util.ArrayList;
import java.util.List;

public class CommodityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = CommodityAdapter.class.getSimpleName();
    private static final int CATEGORY_ITEM_TYPE = 1;
    private static final int COMMODITY_ITEM_TYPE = 2;
    public final Context context;
    public final int root;
    public List<Object> objects;

    public CommodityAdapter(Context context, int root) {
        this.context = context;
        this.root = root;
        this.objects = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType " + String.valueOf(position));
        Object object = objects.get(position);
        if (object instanceof Category) {
            return CATEGORY_ITEM_TYPE;
        } else {
            return COMMODITY_ITEM_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + String.valueOf(position));
        if (holder instanceof CategoryViewHolder) {
            Category category = (Category) objects.get(position);
            CategoryViewHolder h = (CategoryViewHolder) holder;
            h.category = category;
            h.viewTitle.setText(category.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));
            h.viewCount.setText(String.valueOf(category.getIntProperty(TableSchema.CategoryEntry.COLUMN_NAME_ID)));
        } else if (holder instanceof CommodityViewHolder) {
            Commodity commodity = (Commodity) objects.get(position);
            CommodityViewHolder h = (CommodityViewHolder) holder;
            h.commodity = commodity;
            h.viewThumbnail.setImageUrl(
                    commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL),
                    VolleyApi.getInstance().getImageLoader()
            );
            h.viewTitle.setText(commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE));
            boolean support_result = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN) != 0;
            h.viewSupportReturn.setVisibility(support_result ? View.VISIBLE : View.GONE);
            boolean in_discount = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT) != 0;
            h.viewInDiscount.setVisibility(in_discount ? View.VISIBLE : View.GONE);
            boolean in_stock = commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK) != 0;
            h.viewOutofStock.setVisibility(in_stock ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, String.format("onCreateViewHolder view type [%d]", viewType));
        if (viewType == CATEGORY_ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.category, parent, false);
            return new CategoryViewHolder(view, context);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.commodity, parent, false);
            return new CommodityViewHolder(view, context);
        }
    }

    public void clear() {
        objects.clear();
        notifyDataSetChanged();
    }

    public void update() {
        objects.clear();
        List<Category> categories = CategoryApi.getInstance().getCategories(root);
        List<Commodity> commodities = CommodityApi.getInstance().getCommodities(root);
        Log.d(TAG, String.format("get %d categories, %d commodities", categories.size(), commodities.size()));
        for (Category c : categories) {
            objects.add((Object) c);
        }
        for (Commodity c : commodities) {
            objects.add((Object) c);
        }
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context context;
        private TextView viewTitle;
        private TextView viewCount;
        private Category category;

        public CategoryViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            viewTitle = (TextView) view.findViewById(R.id.title);
            viewCount = (TextView) view.findViewById(R.id.count);
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
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.inflate(R.menu.menu_set_category);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_rename:
                            LayoutInflater layoutInflater = LayoutInflater.from(context);
                            View promptView = layoutInflater.inflate(R.layout.dialog_add_category, null);
                            final EditText editText = (EditText) promptView.findViewById(R.id.input);
                            editText.setText(category.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));

                            new AlertDialogWrapper.Builder(context)
                                    .setView(promptView)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String title = editText.getText().toString();
                                            if (!title.isEmpty()) {
                                                category.setStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE, title);
                                                new HttpAsyncTask(context, new CategoryService(),
                                                        new HttpAsyncTask.OnFinishedListener() {
                                                            @Override
                                                            public void onFinished(String errors) {
                                                                if (errors.isEmpty()) {
                                                                    update();
                                                                } else {
                                                                    Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }).doPost(category.toString());
                                            }
                                        }
                                    })
                                    .show();
                            return true;
                        case R.id.menu_remove:
                            new AlertDialogWrapper.Builder(context)
                                    .setMessage(R.string.DIALOG_MESSAGE_DELETE_CATEGORY)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            category.setStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_STATUS, "deleted");
                                            new HttpAsyncTask(context, new CategoryService(),
                                                    new HttpAsyncTask.OnFinishedListener() {
                                                        @Override
                                                        public void onFinished(String errors) {
                                                            if (errors.isEmpty()) {
                                                                update();
                                                            } else {
                                                                Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).doPost(category.toString());
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
            Intent intent = new Intent(context, CommodityListActivity.class);
            intent.putExtra(context.getString(R.string.EXTRA_CATEGORY_ID),
                    category.getIntProperty(TableSchema.CategoryEntry.COLUMN_NAME_ID));
            intent.putExtra(context.getString(R.string.EXTRA_CATEGORY_TITLE),
                    category.getStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE));
            context.startActivity(intent);
        }
    }

    public class CommodityViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context context;
        private NetworkImageView viewThumbnail;
        private TextView viewTitle;
        private TextView viewPrice;
        private TextView viewSales;
        private TextView viewReturns;
        private ImageView viewSupportReturn;
        private ImageView viewInDiscount;
        private ImageView viewOutofStock;
        private Commodity commodity;

        public CommodityViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            viewThumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            viewTitle = (TextView) view.findViewById(R.id.title);
            viewPrice = (TextView) view.findViewById(R.id.price);
            viewSales = (TextView) view.findViewById(R.id.sales);
            viewReturns = (TextView) view.findViewById(R.id.returns);
            viewSupportReturn = (ImageView) view.findViewById(R.id.support_return);
            viewInDiscount = (ImageView) view.findViewById(R.id.in_discount);
            viewOutofStock = (ImageView) view.findViewById(R.id.outof_stock);
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
            PopupMenu popupMenu = new PopupMenu(context, view);
            if (commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK) != 0) {
                popupMenu.inflate(R.menu.menu_set_commodity_1);
            } else {
                popupMenu.inflate(R.menu.menu_set_commodity_2);
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_disable:
                            commodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, 0);
                            new HttpAsyncTask(context, new CommodityService(),
                                    new HttpAsyncTask.OnFinishedListener() {
                                        @Override
                                        public void onFinished(String errors) {
                                            if (errors.isEmpty()) {
                                                update();
                                            } else {
                                                Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).doPost(commodity.toString());
                            return true;
                        case R.id.menu_enable:
                            commodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK, 1);
                            new HttpAsyncTask(context, new CommodityService(),
                                    new HttpAsyncTask.OnFinishedListener() {
                                        @Override
                                        public void onFinished(String errors) {
                                            if (errors.isEmpty()) {
                                                update();
                                            } else {
                                                Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).doPost(commodity.toString());
                            return true;
                        case R.id.menu_remove:
                            new AlertDialogWrapper.Builder(context)
                                    .setMessage(R.string.DIALOG_MESSAGE_DELETE_COMMODITY)
                                    .setNegativeButton(R.string.btn_cancel, null)
                                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            commodity.setStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_STATUS, "deleted");
                                            new HttpAsyncTask(context, new CommodityService(),
                                                    new HttpAsyncTask.OnFinishedListener() {
                                                        @Override
                                                        public void onFinished(String errors) {
                                                            if (errors.isEmpty()) {
                                                                update();
                                                            } else {
                                                                Toast.makeText(GlobalContext.getInstance(), errors, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).doPost(commodity.toString());
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
            Intent intent = new Intent(context, EditCommodityActivity.class);
            intent.putExtra(context.getString(R.string.EXTRA_COMMODITY_ID),
                    commodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_ID));
            intent.putExtra(context.getString(R.string.EXTRA_CATEGORY_ID),
                    root);
            context.startActivity(intent);
        }
    }
}