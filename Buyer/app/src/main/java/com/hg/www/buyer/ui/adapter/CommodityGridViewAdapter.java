package com.hg.www.buyer.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.R;
import com.hg.www.buyer.SystemUtils;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Commodity;
import com.hg.www.buyer.service.VolleyApi;
import com.hg.www.buyer.ui.CartButton;

import java.util.List;

public class CommodityGridViewAdapter extends BaseAdapter {
    private Context mContext;
    public List<Commodity> commodities;
    public static GridViewHolder mHolder = null;
    private int mWidth;
    private int mHeight;

    public CommodityGridViewAdapter(Context c) {
        mContext = c;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        mWidth = (metrics.widthPixels)/2;
        mHeight = mWidth + (mWidth/3);
    }

    public int getCount() {
        return commodities.size();
    }

    public Object getItem(int position) {
        return commodities.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Commodity commodity = commodities.get(position);

        if (convertView == null) {
            mHolder = new GridViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.commodity_grid_item, parent, false);
            mHolder.viewThumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
            mHolder.viewTitle = (TextView) convertView.findViewById(R.id.title);
            mHolder.viewPrice = (TextView) convertView.findViewById(R.id.price);
            mHolder.btnBuy = (Button) convertView.findViewById(R.id.btn_buy);
            mHolder.commodity = commodity;

            mHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GridViewHolder holder = (GridViewHolder) v.getTag();
                    int count = CartButton.getInstance().addCommodity(holder.commodity);
                    holder.btnBuy.setText(String.format("%d", count));
                }
            });

            //Typeface tff = Typeface.createFromAsset(mContext.getAssets(), "fonts/STXiHei.ttf");
            //mHolder.viewTitle.setTypeface(tff);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mHolder.viewThumbnail.getLayoutParams();
            params.width = mWidth;
            params.height = mWidth;
            mHolder.viewThumbnail.setLayoutParams(params);

            mHolder.btnBuy.setTag(mHolder);
            convertView.setTag(mHolder);
        } else {
            mHolder = (GridViewHolder) convertView.getTag();
        }

        mHolder.viewThumbnail.setImageUrl(
                commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL),
                VolleyApi.getInstance().getImageLoader()
        );
        mHolder.viewTitle.setText(commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE));
        mHolder.viewPrice.setText(String.format(GlobalContext.getInstance().getString(R.string.price),
                        commodity.getFloatProperty(TableSchema.CommodityEntry.COLUMN_NAME_PRICE)));
        int count = CartButton.getInstance().getCommodityCount(commodity);
        if (count > 0) {
            mHolder.btnBuy.setText(String.format("%d", count));
        } else {
            mHolder.btnBuy.setText(GlobalContext.getInstance().getString(R.string.btn_buy));
        }

        return convertView;
    }

    public static class GridViewHolder {
        public NetworkImageView viewThumbnail;
        public TextView viewTitle;
        public TextView viewPrice;
        public Button btnBuy;
        public Commodity commodity;
    }
}
