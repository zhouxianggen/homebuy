package com.hg.www.selller.service;

import android.content.Intent;
import android.util.Log;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.api.RestfulApi;
import com.hg.www.selller.data.db.TableSchema;

import org.json.JSONArray;
import org.json.JSONObject;

public class CategoryService extends BasicService {
    public static final String TAG = CategoryService.class.getSimpleName();
    private static final int count = 100;

    public static void startService(String action) {
        Intent intent = new Intent(GlobalContext.getInstance(), CategoryService.class);
        intent.setAction(action);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), CategoryService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    public String get() {
        int lastModifyTime = CategoryApi.getInstance().getLatestModifyTime();
        int sellerId = GlobalContext.getInstance().getSellerId();
        String server = AppSettings.getCategoriesServerAddress();
        String url = String.format("%s?seller_id=%s&if_modified_since=%d&count=%d",
                server, sellerId, lastModifyTime, count);

        Log.d(TAG, String.format("request url is [%s]", url));

        String result = RestfulApi.getInstance().get(url);
        if (result.isEmpty()) {
            Log.d(TAG, "result is empty");
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

            JSONArray categories = object.getJSONArray("categories");
            for (int i = 0; i < categories.length(); i++) {
                JSONObject category = categories.getJSONObject(i);
                int categoryId = category.getInt(TableSchema.CategoryEntry.COLUMN_NAME_ID);
                String categoryStatus = category.getString(TableSchema.CategoryEntry.COLUMN_NAME_STATUS);
                if (categoryStatus.equals("deleted")) {
                    Log.d(TAG, String.format("delete category [%d]", categoryId));
                    CategoryApi.getInstance().deleteCategory(categoryId);
                } else {
                    Log.d(TAG, String.format("replace category [%d]", categoryId));
                    CategoryApi.getInstance().insertCategory(category);
                }
            }
        } catch (org.json.JSONException e) {
            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
            return GlobalContext.getInstance().getString(R.string.REMOTE_SERVER_ERROR);
        }

        return "";
    }

    public String post(String data) {
        String server = AppSettings.getCategoryServerAddress();
        return doPost(server, data);
    }
}
