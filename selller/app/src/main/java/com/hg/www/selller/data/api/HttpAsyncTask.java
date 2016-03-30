package com.hg.www.selller.data.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;

public class HttpAsyncTask extends AsyncTask<Void, Integer, String> {
    public static final String TAG = HttpAsyncTask.class.getSimpleName();
    private final Context context;
    private String method = "GET";
    private String url = "";
    private String data = "";
    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private boolean showProgress = false;
    ProgressDialog progressDialog = null;

    public HttpAsyncTask(Context context, String method, String url, String data,
                         OnSuccessListener onSuccessListener, OnFailureListener onFailureListener,
        boolean showProgress) {
        this.context = context;
        this.method = method;
        this.url = url;
        this.data = data;
        this.onSuccessListener = onSuccessListener;
        this.onFailureListener = onFailureListener;
        this.showProgress = showProgress;
    }

    protected void onPreExecute() {
        if (this.showProgress) {
            progressDialog = ProgressDialog.show(context,
                    GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_TITLE),
                    GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_MESSAGE), true);
        }
    }

    protected String doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        if (method.equals("GET")) {
            return RestfulApi.getInstance().get(url);
        } else if (method.equals("POST")) {
            return RestfulApi.getInstance().post(url, data);
        } else {
            return "";
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, String.format("onPostExecute, result [%s]", result.substring(0, 20)));
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (!result.isEmpty()) {
            onSuccessListener.onSuccess(result);
        } else {
            onFailureListener.onFailure(result);
        }
    }

    protected void onCancelled(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        onFailureListener.onFailure(result);
    }

    public interface OnSuccessListener {
        void onSuccess(String result);
    }

    public interface OnFailureListener {
        void onFailure(String errors);
    }
}
