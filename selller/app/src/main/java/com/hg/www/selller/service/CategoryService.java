package com.hg.www.selller.service;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hg.www.selller.AppSettings;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.db.TableSchema;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class CategoryService extends BasicService {
    public static final String TAG = CategoryService.class.getSimpleName();
    private static final int count = 100;

    public static void startService(String action, String data) {
        Intent intent = new Intent(GlobalContext.getInstance(), CategoryService.class);
        intent.putExtra("data", data);
        intent.setAction(action);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        clearAlarm();
        Intent intent = new Intent(GlobalContext.getInstance(), CategoryService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    @Override
    protected void onActionGet() {
        int lastModifyTime = CategoryApi.getInstance().getLatestModifyTime();
        int sellerId = GlobalContext.getInstance().getSellerId();
        String server = AppSettings.getCategoriesServerAddress();
        String url = String.format("%s?seller_id=%s&if_modified_since=%d&count=%d",
                server, sellerId, lastModifyTime, count);
        Log.d(TAG, String.format("request url is [%s]", url));
        Context context = GlobalContext.getInstance().getApplicationContext();

        new HttpAsyncTask(context, "GET", url, "",
                new HttpAsyncTask.OnSuccessListener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.d(TAG, String.format("result [%s]", result.subSequence(0, 20)));
                            JSONObject object = new JSONObject(result);
                            Iterator it = object.keys();
                            while (it.hasNext()) {
                                String key = (String) it.next();
                                Log.d(TAG, String.format("key [%s]", key));
                            }
                            String status = object.getString("status");
                            if (!status.equals(RESP_STATUS_OK)) {
                                Log.d(TAG, String.format("resp status : [%s]", status));
                            } else {
                                JSONArray categories = object.getJSONArray("categories");
                                for (int i = 0; i < categories.length(); i++) {
                                    JSONObject category = categories.getJSONObject(i);
                                    int categoryId = category.getInt(TableSchema.CategoryEntry.COLUMN_NAME_ID);
                                    String categoryStatus = category.getString(TableSchema.CategoryEntry.COLUMN_NAME_STATUS);
                                    if (categoryStatus.equals("deleted")) {
                                        CategoryApi.getInstance().deleteCategory(categoryId);
                                    } else {
                                        CategoryApi.getInstance().insertCategory(category);
                                    }
                                }
                            }
                        } catch (org.json.JSONException e) {
                            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
                        }
                    }
                },
                new HttpAsyncTask.OnFailureListener() {
                    @Override
                    public void onFailure(String errors) {

                    }
                }, false).execute();
    }

    @Override
    protected void onActionPost(String data) {
        String server = AppSettings.getCategoryServerAddress();
        String url = String.format("%s", server);
        Log.d(TAG, String.format("request url is [%s]", url));
        Context context = GlobalContext.getInstance().getApplicationContext();

        new HttpAsyncTask(context, "POST", url, data,
                new HttpAsyncTask.OnSuccessListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, String.format("result [%s]", result.subSequence(0, 20)));
                        try {
                            JSONObject object = new JSONObject(result);
                            String status = object.getString("status");
                            if (!status.equals(RESP_STATUS_OK)) {
                                Log.d(TAG, String.format("resp status : [%s]", status));
                            } else {
                                onActionGet();
                            }
                        } catch (org.json.JSONException e) {
                            Log.d(TAG, String.format("json exception : [%s]", e.toString()));
                        }
                    }
                },
                new HttpAsyncTask.OnFailureListener() {
                    @Override
                    public void onFailure(String errors) {
                    }
                }, true).execute();
    }
}
