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
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.ExpressmanApi;
import com.hg.www.selller.data.api.MessageApi;
import com.hg.www.selller.data.api.OrderPackageApi;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Expressman;
import com.hg.www.selller.data.define.Message;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.data.define.OrderPackage;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
        mAdapter.messages = MessageApi.getInstance().getMessages();
        Log.d(TAG, "get messages " + String.valueOf(mAdapter.messages.size()));
        mAdapter.packages = OrderPackageApi.getInstance().getPackages();
        Log.d(TAG, "get packages " + String.valueOf(mAdapter.packages.size()));
        mAdapter.notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        EXPRESSMAN_MESSAGE,
        ORDER_PACKAGE
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final Context context;
        public List<Message> messages = new ArrayList<>();
        public List<OrderPackage> packages = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return messages.size() + packages.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < messages.size()) {
                return ITEM_TYPE.EXPRESSMAN_MESSAGE.ordinal();
            } else {
                return ITEM_TYPE.ORDER_PACKAGE.ordinal();
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ExpressmanMessageViewHolder) {
                Message message = messages.get(position);
                ((ExpressmanMessageViewHolder) holder).mMessage = message;
                Expressman expressman = ExpressmanApi.getInstance().getExpressman(
                        message.getIntProperty(TableSchema.ExpressmanEntry.COLUMN_NAME_ID));
                if (expressman != null) {
                    ((ExpressmanMessageViewHolder) holder).mExpressmanIcon.setImageUrl(
                            expressman.getStringProperty(TableSchema.ExpressmanEntry.COLUMN_NAME_THUMBNAIL),
                            VolleyApi.getInstance().getImageLoader()
                    );
                    ((ExpressmanMessageViewHolder) holder).mExpressmanName.setText(
                            expressman.getStringProperty(TableSchema.ExpressmanEntry.COLUMN_NAME_NAME));
                }
                ((ExpressmanMessageViewHolder) holder).mTime.setText(
                        message.getStringProperty(TableSchema.MessageEntry.COLUMN_NAME_MODIFY_TIME));
                ((ExpressmanMessageViewHolder) holder).mContent.setText(
                        message.getStringProperty(TableSchema.MessageEntry.COLUMN_NAME_CONTENT));
            } else if (holder instanceof OrderPackageViewHolder) {
                OrderPackage orderPackage = packages.get(position - messages.size());
                ((OrderPackageViewHolder) holder).mPackage = orderPackage;
                ((OrderPackageViewHolder) holder).mTitle.setText(orderPackage.getTitle());
                ((OrderPackageViewHolder) holder).mAount.setText(String.valueOf(orderPackage.getAmount()));
                ((OrderPackageViewHolder) holder).mPayment.setText(String.valueOf(orderPackage.getPayment()));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.EXPRESSMAN_MESSAGE.ordinal()) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.expressman_message, parent, false);
                return new ExpressmanMessageViewHolder(view, context);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.order_package, parent, false);
                return new OrderPackageViewHolder(view, context);
            }
        }

        public class ExpressmanMessageViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private Context mContext;
            private NetworkImageView mExpressmanIcon;
            private TextView mExpressmanName;
            private TextView mContent;
            private TextView mTime;
            private Message mMessage;

            public ExpressmanMessageViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mExpressmanIcon = (NetworkImageView) view.findViewById(R.id.expressman_icon);
                mExpressmanName = (TextView) view.findViewById(R.id.expressman_name);
                mContent = (TextView) view.findViewById(R.id.content);
                mTime = (TextView) view.findViewById(R.id.time);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PrepareOrderListActivity.class);
                intent.putExtra(mContext.getString(R.string.EXTRA_MESSAGE_ID), mMessage.getIntProperty(TableSchema.MessageEntry.COLUMN_NAME_ID));
                intent.putExtra(mContext.getString(R.string.EXTRA_EXPRESSMAN_ID), mMessage.getIntProperty(TableSchema.MessageEntry.COLUMN_NAME_SENDER));
                mContext.startActivity(intent);
            }
        }

        public class OrderPackageViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private Context mContext;
            private TextView mTitle;
            private TextView mAount;
            private TextView mPayment;
            private OrderPackage mPackage;

            public OrderPackageViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mTitle = (TextView) view.findViewById(R.id.title);
                mAount = (TextView) view.findViewById(R.id.amount);
                mPayment = (TextView) view.findViewById(R.id.payment);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mPackage.status.equals(Order.STATUS_NEW)) {
                    Intent intent = new Intent(mContext, NewOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status.equals(Order.STATUS_ACCEPTED)) {
                    Intent intent = new Intent(mContext, AcceptedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status.equals(Order.STATUS_LOADED)) {
                    Intent intent = new Intent(mContext, LoadedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status.equals(Order.STATUS_PAID)) {
                    Intent intent = new Intent(mContext, PaidOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status.equals(Order.STATUS_RETURNED)) {
                    Intent intent = new Intent(mContext, ReturnedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status.equals(Order.STATUS_CLAIMED)) {
                    Intent intent = new Intent(mContext, ClaimedOrderListActivity.class);
                    mContext.startActivity(intent);
                }
            }
        }
    }

}
