package com.hg.www.selller;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hg.www.selller.data.db.DbHelper;
import com.hg.www.selller.service.BasicService;
import com.hg.www.selller.service.CategoryService;

import java.util.HashMap;

/**
 * 全局环境
 */
public class GlobalContext extends Application {
    private static final String TAG = GlobalContext.class.getSimpleName();
    private static GlobalContext mContext;
    private static String mServer = "";
    private static HashMap<String, String> mPathTimestamps = new HashMap<>();
    private static int seller_id = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        DbHelper mDbHelper = new DbHelper(GlobalContext.getInstance());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        mDbHelper.onUpgrade(db, 0, 0);
        Log.d(TAG, "global context start server");
        CategoryService.startService(BasicService.ACTION_GET, "");
    }

    public static GlobalContext getInstance() {
        return mContext;
    }

    public int getSellerId() { return seller_id; }

    public String getUri(String path) {
        String timestamp = mPathTimestamps.get(path);
        if (timestamp == null) {
            timestamp = "";
        }
        return mServer + path + "?timestamp=" + timestamp;
    }

    public void setPathTimestamp(String path, String timestamp) {
        mPathTimestamps.put(path, timestamp);
    }
}
