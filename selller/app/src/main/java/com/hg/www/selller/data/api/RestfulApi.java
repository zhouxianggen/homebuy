package com.hg.www.selller.data.api;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestfulApi {
    public static final String TAG = RestfulApi.class.getSimpleName();
    private static RestfulApi instance = null;

    private RestfulApi() {
    }

    public static RestfulApi getInstance() {
        if (instance == null) {
            instance = new RestfulApi();
        }
        return instance;
    }

    public String get(String address) {
        try {
            Log.d(TAG, String.format("get from [%s]", address));
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return IOUtils.toString(in, "UTF-8");
        } catch (Exception e) {
            Log.d(TAG, String.format("exception: [%s]", e.toString()));
            return "";
        }
    }

    public String post(String address, String data) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return IOUtils.toString(in, "UTF-8");
        } catch (Exception e) {
            Log.d(TAG, String.format("exception: [%s]", e.toString()));
            return "";
        }
    }
}
