package com.hg.www.buyer.ui.event;

public class ServicePullDataFinishedEvent {
    public static String SERVICE_SELLER = "seller";
    public static String SERVICE_ORDER = "order";
    public static String SERVICE_COMMODITY = "commodity";
    public String service = "";
    public String errors = "";

    public ServicePullDataFinishedEvent(String service, String errors) {
        this.service = service;
        this.errors = errors;
    }
}
