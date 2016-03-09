package com.hg.www.selller.data.define;

public class ExpressmanMessage {
    public static String STATUS_NEW = "new";
    public static String STATUS_CHECKED = "checked";
    public static String PREPARE_ORDER = "PREPARE_ORDER";
    public static String CHECK_LOADING_ORDER = "CHECK_LOADING_ORDER";
    public static String CHECK_RETURN_ORDER = "CHECK_RETURN_ORDER";
    public String id = "";
    public String type = "";
    public String expressman_id = "";
    public String timestamp = "";
    public String content = "";
    public String status = "";
    
    public boolean parseFromString(String s) {
        expressman_id = "expressman_1";
        timestamp = "1";
        type =  CHECK_LOADING_ORDER;
        return true;
    }
}
