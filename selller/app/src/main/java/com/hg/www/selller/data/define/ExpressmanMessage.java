package com.hg.www.selller.data.define;

public class ExpressmanMessage {
    public static String MESSAGE_TYPE_PREPARE_ORDER = "PREPARE";
    public static String MESSAGE_TYPE_CHECK_LOADED_ORDER = "CHECK_LOADED_ORDER";
    public String id = "";
    public String type = "";
    public Expressman expressman = null;
    public String time = "";
    public String content = "";
}
