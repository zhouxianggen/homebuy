package com.hg.www.buyer.service;

public interface ServiceInterface {
    public String pull(String args);
    public String push(String data);
    public void onFinished(String errors);
}
