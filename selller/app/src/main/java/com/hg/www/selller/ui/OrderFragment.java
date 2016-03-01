package com.hg.www.selller.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.www.selller.R;
import com.hg.www.selller.data.api.ExpressmanMessageApi;
import com.hg.www.selller.data.api.OrderPackageApi;
import com.hg.www.selller.data.define.ExpressmanMessage;
import com.hg.www.selller.data.define.Order;
import com.hg.www.selller.data.define.OrderPackage;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        onUpdateAdapter();

        return rootView;
    }

    public void onUpdateAdapter() {
        mAdapter.expressmanMessages = ExpressmanMessageApi.getInstance().getMessages();
        mAdapter.orderPackages = OrderPackageApi.getInstance().getPackages();
        mAdapter.notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        EXPRESSMAN_MESSAGE,
        ORDER_PACKAGE
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final Context context;
        public List<ExpressmanMessage> expressmanMessages = new ArrayList<>();
        public List<OrderPackage> orderPackages = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return expressmanMessages.size() + orderPackages.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < expressmanMessages.size()) {
                return ITEM_TYPE.EXPRESSMAN_MESSAGE.ordinal();
            } else {
                return ITEM_TYPE.ORDER_PACKAGE.ordinal();
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ExpressmanMessageViewHolder) {
                ExpressmanMessage message = expressmanMessages.get(position);
                ((ExpressmanMessageViewHolder) holder).mMessage = message;
                //((ExpressmanMessageViewHolder) holder).mExpressmanIcon.set(message.expressman.icon);
                ((ExpressmanMessageViewHolder) holder).mExpressmanName.setText(message.expressman.name);
                ((ExpressmanMessageViewHolder) holder).mTime.setText(message.time);
                ((ExpressmanMessageViewHolder) holder).mContent.setText(message.content);
            } else if (holder instanceof OrderPackageViewHolder) {
                OrderPackage orderPackage = orderPackages.get(position - expressmanMessages.size());
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
            private ImageView mExpressmanIcon;
            private TextView mExpressmanName;
            private TextView mContent;
            private TextView mTime;
            private ExpressmanMessage mMessage;

            public ExpressmanMessageViewHolder(View view, Context context) {
                super(view);
                mContext = context;
                mExpressmanIcon = (ImageView) view.findViewById(R.id.expressman_icon);
                mExpressmanName = (TextView) view.findViewById(R.id.expressman_name);
                mContent = (TextView) view.findViewById(R.id.content);
                mTime = (TextView) view.findViewById(R.id.time);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PrepareOrderListActivity.class);
                intent.putExtra(mContext.getString(R.string.EXTRA_EXPRESSMAN_ID), mMessage.expressman.id);
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
                if (mPackage.status == Order.STATUS_NEW) {
                    Intent intent = new Intent(mContext, NewOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status == Order.STATUS_ACCEPTED) {
                    Intent intent = new Intent(mContext, AcceptedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status == Order.STATUS_LOADED) {
                    Intent intent = new Intent(mContext, LoadedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status == Order.STATUS_PAID) {
                    Intent intent = new Intent(mContext, PaidOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status == Order.STATUS_RETURNED) {
                    Intent intent = new Intent(mContext, ReturnedOrderListActivity.class);
                    mContext.startActivity(intent);
                } else if (mPackage.status == Order.STATUS_CLAIMED) {
                    Intent intent = new Intent(mContext, ClaimedOrderListActivity.class);
                    mContext.startActivity(intent);
                }
            }
        }
    }

}
