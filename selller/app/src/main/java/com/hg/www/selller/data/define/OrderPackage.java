package com.hg.www.selller.data.define;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;

import java.util.List;

public class OrderPackage {
    public String status = "";
    public List<Order> orders = null;

    public int getAmount() {
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

    public double getPayment() {
        if (orders == null) {
            return 0;
        }
        double payment = 0;
        for (Order order : orders) {
            payment += order.payment;
        }
        return payment;
    }

    public String getTitle() {
        if (status.equals(Order.STATUS_NEW)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_NEW);
        } else if (status.equals(Order.STATUS_ACCEPTED)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_ACCEPTED);
        } else if (status.equals(Order.STATUS_LOADED)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_LOADED);
        } else if (status.equals(Order.STATUS_PAID)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_PAID);
        } else if (status.equals(Order.STATUS_RETURNED)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_RETURNED);
        } else if (status.equals(Order.STATUS_CLAIMED)) {
            return GlobalContext.getInstance().getString(R.string.ORDER_STATUS_CLAIMED);
        } else {
            return "";
        }
    }
}
