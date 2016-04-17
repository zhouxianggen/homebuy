package com.hg.www.buyer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.buyer.R;
import com.hg.www.buyer.data.api.SellerApi;
import com.hg.www.buyer.service.VolleyApi;
import com.hg.www.buyer.data.db.TableSchema;
import com.hg.www.buyer.data.define.Seller;
import com.hg.www.buyer.ui.BuyActivity;

import java.util.ArrayList;
import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SellerAdapter.class.getSimpleName();
    private static final int ITEM_VIEW_TYPE_SELLER = 1;
    private Context context;
    private List<Object> objects;

    public SellerAdapter(Context context) {
        this.context = context;
        this.objects = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_SELLER;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + String.valueOf(position));
        Seller seller = (Seller) objects.get(position);
        SellerViewHolder h = (SellerViewHolder) holder;
        h.seller = seller;
        h.viewThumbnail.setImageUrl(
                seller.getStringProperty(TableSchema.SellerEntry.COLUMN_NAME_THUMBNAIL),
                VolleyApi.getInstance().getImageLoader()
        );
        h.viewTitle.setText(seller.getStringProperty(TableSchema.SellerEntry.COLUMN_NAME_TITLE));
        h.viewAddress.setText(seller.getStringProperty(TableSchema.SellerEntry.COLUMN_NAME_ADDRESS));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seller_list_item, parent, false);
        return new SellerViewHolder(view, context);
    }

    public void clear() {
        objects.clear();
        notifyDataSetChanged();
    }

    public void update() {
        objects.clear();
        List<Seller> sellers = SellerApi.getInstance().getSellers();
        Log.d(TAG, String.format("get %d sellers", sellers.size()));
        for (Seller seller : sellers) {
            objects.add((Seller) seller);
        }
        notifyDataSetChanged();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Context context;
        private NetworkImageView viewThumbnail;
        private TextView viewTitle;
        private TextView viewAddress;
        private Seller seller;

        public SellerViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            viewThumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            viewTitle = (TextView) view.findViewById(R.id.title);
            viewAddress = (TextView) view.findViewById(R.id.address);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, BuyActivity.class);
            intent.putExtra(context.getString(R.string.extra_seller_id),
                    seller.getIntProperty(TableSchema.SellerEntry.COLUMN_NAME_ID));
            intent.putExtra(context.getString(R.string.extra_seller_title),
                    seller.getStringProperty(TableSchema.SellerEntry.COLUMN_NAME_TITLE));
            context.startActivity(intent);
        }
    }
}
