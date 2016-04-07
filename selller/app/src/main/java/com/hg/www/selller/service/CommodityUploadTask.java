package com.hg.www.selller.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.define.Commodity;

import java.util.List;

public class CommodityUploadTask extends AsyncTask<Commodity, Integer, String> {
    public static final String TAG = CommodityUploadTask.class.getSimpleName();
    private final Context context;
    ProgressDialog progressDialog = null;
    private String errors = "";

    public CommodityUploadTask(Context context) {
        this.context = context;
    }

    public String upload(Commodity commodity) {
        execute(commodity);
        return errors;
    }

    private void dismissDialog() {
        try {
            if ((this.progressDialog != null) && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.progressDialog = null;
        }
    }

    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,
                GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_TITLE),
                GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_MESSAGE), true);
    }

    protected String doInBackground(Commodity... params) {
        Log.d(TAG, "doInBackground");
        Commodity commodity = params[0];
        List<String> paths = commodity.getImages();
        List<String> urls = ImageService.upload(paths, 30000);
        if (urls != null) {
            commodity.deleteImages();
            for (String url : urls) {
                commodity.addImage(url);
            }
            return new CommodityService().post(commodity.toString());
        }
        return GlobalContext.getInstance().getString(R.string.REMOTE_SERVER_ERROR);
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        dismissDialog();
        errors = result;
    }

    protected void onCancelled(String result) {
        dismissDialog();
    }
}
