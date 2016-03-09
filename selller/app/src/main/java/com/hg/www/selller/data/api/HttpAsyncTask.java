package com.hg.www.selller.data.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;

import java.io.IOException;
import java.net.URL;

public class HttpAsyncTask extends AsyncTask<Void, Integer, String> {
    private final Context mContext;
    private String mMethod = "GET";
    private String mUrl = "";
    private String mData = "";
    private OnSuccessListener mOnSuccessListener;
    private OnFailureListener mOnFailureListener;
    ProgressDialog mProgressDialog;

    public HttpAsyncTask(Context context, String method, String url, String data,
                         OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        mContext = context;
        mMethod = method;
        mUrl = url;
        mData = data;
        mOnSuccessListener = onSuccessListener;
        mOnFailureListener = onFailureListener;
    }

    protected void onPreExecute() {
//        mProgressDialog = new ProgressDialog(mContext);
//        mProgressDialog.setTitle(GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_TITLE));
//        mProgressDialog.setMessage(GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_MESSAGE));
//        mProgressDialog.setCancelable(true);
//        mProgressDialog.show();
        mProgressDialog = ProgressDialog.show(mContext,
                GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_TITLE),
                GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_MESSAGE), true);
    }

    protected String doInBackground(Void... params) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        mProgressDialog.dismiss();
        if (result.isEmpty()) {
            mOnSuccessListener.onSuccess();
        } else {
            mOnFailureListener.onFailure(result);
        }
    }

    protected void onCancelled(String result) {
        mProgressDialog.dismiss();
        mOnFailureListener.onFailure(result);
    }

    public interface OnSuccessListener {
        void onSuccess();
    }

    public interface OnFailureListener {
        void onFailure(String errors);
    }
}
