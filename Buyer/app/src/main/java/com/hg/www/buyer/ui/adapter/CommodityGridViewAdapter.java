package com.hg.www.buyer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.buyer.R;
import com.hg.www.buyer.SystemUtils;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Commodity;
import com.hg.www.buyer.service.VolleyApi;

import java.util.List;

public class CommodityGridViewAdapter extends BaseAdapter {
    private Context mContext;
    public List<Commodity> commodities;

    public CommodityGridViewAdapter(Context c) {
        mContext = c;
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
        View view;
        Commodity commodity = commodities.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.commodity_grid_item, parent, false);
            NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.thumbnail);
            imageView.setImageUrl(
                    commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL),
                    VolleyApi.getInstance().getImageLoader()
            );
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE));
            TextView price = (TextView) view.findViewById(R.id.price);
            price.setText(String.valueOf(commodity.getFloatProperty(TableSchema.CommodityEntry.COLUMN_NAME_PRICE)));
            int width = SystemUtils.getScreenWidth() / 3;
            view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, (int)(width * 1.5)));
        } else {
            view = convertView;
        }
        return view;
    }
}
