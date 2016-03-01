package com.hg.www.selller.data.define;

public class Order {
    public static String STATUS_NEW = "new";
    public static String STATUS_ACCEPTED = "accepted";
    public static String STATUS_LOADED = "loaded";
    public static String STATUS_PAID = "paid";
    public static String STATUS_RETURNED = "returned";
    public static String STATUS_CLAIMED = "claimed";

    public String id = "";
    public String agency_id = "";
    public String seller_id = "";
    public String commodity_id = "";
    public String expressman_id = "";
    public int amount = 0;
    public double payment = 0;
    public String status = "";
}
