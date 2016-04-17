package com.hg.www.buyer;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hg.www.buyer.data.db.DbHelper;
import com.hg.www.buyer.service.BasicService;
import com.hg.www.buyer.service.SellerService;

/**
 * 全局环境
 */
public class GlobalContext extends Application {
    private static final String TAG = GlobalContext.class.getSimpleName();
    private static GlobalContext mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        DbHelper mDbHelper = new DbHelper(GlobalContext.getInstance());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        mDbHelper.onUpgrade(db, 0, 0);

        SellerService.startPull(String.format("area=0"));
    }

    public static GlobalContext getInstance() {
        return mContext;
    }
}
