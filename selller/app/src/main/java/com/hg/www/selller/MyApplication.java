package com.hg.www.selller;

import android.app.Application;

import com.hg.www.selller.data.DataManager;

public class MyApplication extends Application {
    private DataManager mDataManager;

    public DataManager getDataManager() {
        if (mDataManager == null) {
            mDataManager = new DataManager(this);
        }
        return mDataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataManager = new DataManager(this);
    }
}
