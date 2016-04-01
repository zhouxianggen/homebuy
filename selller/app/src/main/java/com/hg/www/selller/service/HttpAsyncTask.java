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
    private String result;
    ProgressDialog progressDialog = null;

    public HttpAsyncTask(Context context, HttpInterface httpInterface) {
        this.context = context;
        this.httpInterface = httpInterface;
    }

    public String doGet() {
        execute();
        return result;
    }

    public String doPost(String data) {
        execute(data);
        return result;
    }

    protected void onPreExecute() {
        this.result = "";
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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        this.result = result;
    }

    protected void onCancelled(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
