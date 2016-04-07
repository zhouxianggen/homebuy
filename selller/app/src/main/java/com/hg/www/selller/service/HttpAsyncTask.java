package com.hg.www.selller.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;

public class HttpAsyncTask extends AsyncTask<String, Integer, String> {
    public static final String TAG = HttpAsyncTask.class.getSimpleName();
    private final Context context;
    private HttpInterface httpInterface;
    private OnFinishedListener onFinishedListener = new OnFinishedListener() {
        @Override
        public void onFinished(String errors) {

        }
    };
    ProgressDialog progressDialog = null;

    public HttpAsyncTask(Context context, HttpInterface httpInterface, OnFinishedListener onFinishedListener) {
        this.context = context;
        this.httpInterface = httpInterface;
        if (onFinishedListener != null) {
            this.onFinishedListener = onFinishedListener;
        }
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

    public void doGet() {
        execute();
    }

    public void doPost(String data) {
        execute(data);
    }

    protected void onPreExecute() {
        if (context != null) {
            progressDialog = ProgressDialog.show(context,
                    GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_TITLE),
                    GlobalContext.getInstance().getString(R.string.PROGRESS_DIALOG_MESSAGE), true);
        }
    }

    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground");
        int count = params.length;
        if (count == 0) { // GET
            return httpInterface.get();
        } else {
            for (int i = 0; i < count; i++) {
                String errors = httpInterface.post(params[i]);
                if (!errors.isEmpty()) {
                    return errors;
                }
            }
            return "";
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, String.format("onPostExecute, result [%s]", result));
        dismissDialog();
        this.onFinishedListener.onFinished(result);
    }

    protected void onCancelled(String result) {
        dismissDialog();
    }

    public interface OnFinishedListener {
        void onFinished(String errors);
    }
}
