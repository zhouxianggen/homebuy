package com.hg.www.selller.ui.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.OrderApi;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.service.BasicService;
import com.hg.www.selller.service.OrderService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context context = null;
    private String type = "";
    public List<Order> orders = new ArrayList<>();

    public OrderAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "bind view holder " + String.valueOf(position));
        final Order order = orders.get(position);
        OrderViewHolder h = (OrderViewHolder) holder;

        int commodityId = order.getIntProperty(TableSchema.OrderEntry.COLUMN_NAME_COMMODITY_ID);
        Commodity commodity = CommodityApi.getInstance().getCommodity(commodityId);
        if (commodity == null) {
            Log.e(TAG, String.format("can not find commodity [%d]", commodityId));
        } else {
            h.viewThumbnail.setImageUrl(
                    commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL),
                    VolleyApi.getInstance().getImageLoader()
            );
            h.viewTitle.setText(
                    commodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE)
            );
        }
        h.viewOrderInfo.setText("fix here");
        h.viewAgency.setText("fix here");

        if (type == Order.STATUS_NEW) {
            h.btnAction.setVisibility(View.VISIBLE);
            h.btnAction.setText(GlobalContext.getInstance().getString(R.string.btn_accept));
            h.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialogWrapper.Builder(context)
                            .setMessage(R.string.DIALOG_MESSAGE_REJECT_ORDER)
                            .setNegativeButton(R.string.btn_cancel, null)
                            .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    order.setStringProperty(
                                            TableSchema.OrderEntry.COLUMN_NAME_STATUS, Order.STATUS_REJECTED);
                                    OrderService.startService(BasicService.ACTION_POST, order.toString());
                                }

                            })
                            .show();
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order, parent, false);
        return new OrderViewHolder(view, context);
    }

    public void onUpdate() {
        Log.d(TAG, "on update");
        orders = OrderApi.getInstance().getOrders(type);
        notifyDataSetChanged();
    }

    public double getTotalPayment() {
        double summary = 0;
        for (Order order : orders) {
            summary += order.getFloatProperty(TableSchema.OrderEntry.COLUMN_NAME_PAYMENT);
        }
        return summary;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private NetworkImageView viewThumbnail;
        private TextView viewTitle;
        private TextView viewOrderInfo;
        private TextView viewAgency;
        private TextView viewSellerInfo;
        private Button btnAction;

        public OrderViewHolder(View view, Context context) {
            super(view);
            viewThumbnail = (NetworkImageView) view.findViewById(R.id.commodity_thumbnail);
            viewTitle = (TextView) view.findViewById(R.id.commodity_title);
            viewOrderInfo = (TextView) view.findViewById(R.id.order_info);
            viewAgency = (TextView) view.findViewById(R.id.agency);
            viewSellerInfo = (TextView) view.findViewById(R.id.seller_info);
            btnAction = (Button) view.findViewById(R.id.btn_action);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
