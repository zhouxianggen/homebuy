package com.hg.www.buyer.service;

import android.content.Intent;
import android.util.Log;

import com.hg.www.buyer.AppSettings;
import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.R;
import com.hg.www.buyer.data.api.SellerApi;
import com.hg.www.buyer.data.define.PullOption;
import com.hg.www.buyer.ui.event.BusApi;
import com.hg.www.buyer.ui.event.ServicePullDataFinishedEvent;

import org.json.JSONArray;
import org.json.JSONObject;


public class SellerService extends BasicService {
    public static final String TAG = SellerService.class.getSimpleName();

    public static void startPush(String data) {
        Intent intent = new Intent(GlobalContext.getInstance(), SellerService.class);
        intent.putExtra(GlobalContext.getInstance().getString(R.string.extra_post_data), data);
        intent.setAction(BasicService.ACTION_PUSH);
        GlobalContext.getInstance().startService(intent);
    }

    public static void startPull(String args) {
        Intent intent = new Intent(GlobalContext.getInstance(), SellerService.class);
        intent.putExtra(GlobalContext.getInstance().getString(R.string.extra_pull_args), args);
        intent.setAction(BasicService.ACTION_PULL);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), SellerService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    public String pull(String args) {
        String server = AppSettings.getSellersServerAddress();
        String url = String.format("%s?%s", server, args);

        Log.d(TAG, String.format("request url is [%s]", url));
        String resp = get(url);
        Log.d(TAG, String.format("resp len is [%d]", resp.length()));
        if (resp.isEmpty()) {
            return GlobalContext.getInstance().getString(R.string.error_remote_server_failed);
        }

        try {
            Log.d(TAG, String.format("result [%s]", resp));
            JSONObject object = new JSONObject(resp);
            String status = object.getString("status");
            if (!status.equals(RESP_STATUS_OK)) {
                Log.d(TAG, String.format("resp status : [%s]", status));
                return GlobalContext.getInstance().getString(R.string.error_remote_server_failed);
            }

            JSONArray sellers = object.getJSONArray("sellers");
            for (int i = 0; i < sellers.length(); i++) {
                JSONObject seller = sellers.getJSONObject(i);
                SellerApi.getInstance().sync(seller);
            }
        } catch (org.json.JSONException e) {
            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
            return GlobalContext.getInstance().getString(R.string.error_remote_server_failed);
        }

        return "";
    }

    public String push(String data) {
        return "";
    }

    public void onFinished(String error) {
        BusApi.getInstance().postToMainThread(new ServicePullDataFinishedEvent(
                ServicePullDataFinishedEvent.SERVICE_SELLER, error
        ));
    }
}
