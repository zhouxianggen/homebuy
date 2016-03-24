package com.hg.www.selller.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.data.define.CommodityCategory;
import com.hg.www.selller.data.define.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 同步商品信息的后台服务
 */
public class CommodityService extends Service {
    public static final String TAG = CommodityService.class.getSimpleName();
    public static final String ACTION_GET = "com.hg.www.ACTION_GET";
    public static final String ACTION_UPDATE = "com.hg.www.ACTION_UPDATE";

    public static void startService() {
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        intent.setAction(ACTION_GET);
        GlobalContext.getInstance().startService(intent);
    }

    public static void stopService() {
        Intent intent = new Intent(GlobalContext.getInstance(), CommodityService.class);
        GlobalContext.getInstance().stopService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : "";

        if (ACTION_GET.equals(action)) {
        }
        else if (ACTION_UPDATE.equals(action)) {
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "on destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static List<Object>  getCommodities(String root) {
        String path = "/commodities/" + root;
        String uri = GlobalContext.getInstance().getUri(path);

        final List<Object> objects = new ArrayList<>();
        /*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String type = jsonObject.getString("type");
                        if (type.equals("commodity")) {
                            Commodity commodity = new Commodity();
                            if (commodity.parse(jsonObject) != null) {
                                objects.add(commodity);
                            }
                        } else if (type.equals("commodity_category")) {
                            CommodityCategory category = new CommodityCategory();
                            if (category.parse(jsonObject) != null) {
                                objects.add(category);
                            }
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "jsonexception at " + e.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/
        return objects;
    }

    static public void updateServerCommodity(
            Context context,
            Commodity commodity,
            int timeout,
            HttpAsyncTask.OnSuccessListener onSuccessListener,
            HttpAsyncTask.OnFailureListener onFailureListener) {
        new HttpAsyncTask(context, "", "", "", onSuccessListener, onFailureListener).execute();
    }

    static public void updateServerCategory(
            Context context,
            CommodityCategory category,
            int timeout,
            HttpAsyncTask.OnSuccessListener onSuccessListener,
            HttpAsyncTask.OnFailureListener onFailureListener) {
        new HttpAsyncTask(context, "", "", "", onSuccessListener, onFailureListener).execute();
    }
}
