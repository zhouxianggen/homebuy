package com.hg.www.selller.service;

import android.util.Log;

import com.hg.www.selller.ui.toolkit.BitmapToolkit;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.UpCompleteListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageService {
    public static final String TAG = ImageService.class.getSimpleName();
    private static String HOST = "http://homebuy.b0.upaiyun.com/";
    private static String KEY = "b0R+D0K3qX/vQ+72ycLpk+K96FE=";
    private static String savePath = "/uploads/{year}{mon}{day}/{random32}{.suffix}";
    private static List<String> urls = null;

    private static synchronized void addUrl(String url) {
        urls.add(url);
    }

    private static synchronized int getUrlCount() {
        return urls.size();
    }

    public static List<String> upload(List<String> paths, int timeout) {
        urls = new ArrayList<>();
        for (String path : paths) {
            if (path.toString().startsWith("http://") || path.toString().startsWith("https://")) {
                addUrl(path);
            } else {
                Log.d(TAG, String.format("upload [%s]", path));
                String tmpPath = BitmapToolkit.compress(path, 600, 800);
                File file = new File(tmpPath);
                Log.d(TAG, String.format("upload tmp [%s]", tmpPath));
                final Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put(Params.BUCKET, "homebuy");
                paramsMap.put(Params.SAVE_KEY, savePath);

                UpCompleteListener completeListener = new UpCompleteListener() {
                    @Override
                    public void onComplete(boolean isSuccess, String resp) {
                        Log.d(TAG, isSuccess + ":" + resp);
                        if (isSuccess) {
                            try {
                                JSONObject object = new JSONObject(resp);
                                String url = HOST + object.getString("url");
                                addUrl(url);
                            } catch (org.json.JSONException e) {
                                addUrl("");
                            }
                        } else {
                            addUrl("");
                        }
                    }
                };

                Log.d(TAG, "start upload");
                UploadManager.getInstance().formUpload(file, paramsMap, KEY, completeListener, null);
            }
        }

        for (int i = 0, ms = 1000; i < timeout; i += ms) {
            Log.d(TAG, String.format("url count is [%d]", getUrlCount()));
            if (getUrlCount() != paths.size()) {
                try {
                    Thread.sleep(ms);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else {
                for (String url : urls) {
                    if (url.isEmpty()) {
                        return null;
                    }
                }
                return urls;
            }
        }
        Log.d(TAG, "timeout!");
        return null;
    }
}
