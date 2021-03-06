package com.hg.www.selller.service;

import android.content.Intent;
import android.util.Log;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.RestfulApi;
import com.hg.www.selller.data.db.TableSchema;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * 同步商品信息的后台服务
 */
public class CommodityService extends BasicService {
    public static final String TAG = CommodityService.class.getSimpleName();
    private static final int count = 100;

    public static void startService(String action) {
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        intent.setAction(action);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    public String get() {
        int lastModifyTime = CommodityApi.getInstance().getLatestModifyTime();
        int sellerId = GlobalContext.getInstance().getSellerId();
        String server = AppSettings.getCommoditiesServerAddress();
        String url = String.format("%s?seller_id=%s&if_modified_since=%d&count=%d",
                server, sellerId, lastModifyTime, count);

        Log.d(TAG, String.format("request url is [%s]", url));

        String result = RestfulApi.getInstance().get(url);
        if (result.isEmpty()) {
            return GlobalContext.getInstance().getString(R.string.REMOTE_SERVER_ERROR);
        }

        try {
            Log.d(TAG, String.format("result [%s]", result.subSequence(0, 20)));
            JSONObject object = new JSONObject(result);
            String status = object.getString("status");
            if (!status.equals(RESP_STATUS_OK)) {
                Log.d(TAG, String.format("resp status : [%s]", status));
                return GlobalContext.getInstance().getString(R.string.REMOTE_SERVER_ERROR);
            }

            JSONArray commodities = object.getJSONArray("commodities");
            for (int i = 0; i < commodities.length(); i++) {
                JSONObject commodity = commodities.getJSONObject(i);
                int id = commodity.getInt(TableSchema.CategoryEntry.COLUMN_NAME_ID);
                String commodityStatus = commodity.getString(TableSchema.CategoryEntry.COLUMN_NAME_STATUS);
                if (commodityStatus.equals("deleted")) {
                    Log.d(TAG, String.format("delete commodity [%d]", id));
                    CommodityApi.getInstance().deleteCommodity(id);
                } else {
                    Log.d(TAG, String.format("replace commodity [%d]", id));
                    CommodityApi.getInstance().insertCommodity(commodity);
                }
            }
        } catch (org.json.JSONException e) {
            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
            return GlobalContext.getInstance().getString(R.string.REMOTE_SERVER_ERROR);
        }

        return "";
    }

    public String post(String data) {
        String server = AppSettings.getCommodityServerAddress();
        String errors = doPost(server, data);
        if (errors.isEmpty()) {
            return get();
        } else {
            Log.d(TAG, String.format("post get errors [%s]", errors));
            return errors;
        }
    }
}
