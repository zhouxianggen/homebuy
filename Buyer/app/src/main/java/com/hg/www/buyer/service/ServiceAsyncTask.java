package com.hg.www.buyer.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hg.www.buyer.GlobalContext;
import com.hg.www.buyer.R;

public class ServiceAsyncTask extends AsyncTask<String, Integer, String> {
    public static final String TAG = ServiceAsyncTask.class.getSimpleName();
    private final Context context;
    private ServiceInterface serviceInterface;
    ProgressDialog progressDialog = null;

    public ServiceAsyncTask(Context context, ServiceInterface serviceInterface) {
        this.context = context;
        this.serviceInterface = serviceInterface;
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

    public void pull(String args) {
        execute("PULL", args);
    }

    public void push(String data) {
        execute("PUSH", data);
    }

    protected void onPreExecute() {
        if (context != null) {
            progressDialog = ProgressDialog.show(context,
                    GlobalContext.getInstance().getString(R.string.process_dialog_title),
                    GlobalContext.getInstance().getString(R.string.process_dialog_content), true);
        }
    }

    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground");
        int count = params.length;
        if (count == 2) {
            if (params[0].equals("PULL")) {
                return serviceInterface.pull(params[1]);
            } else if (params[0].equals("PUSH")) {
                return serviceInterface.push(params[1]);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, String.format("onPostExecute, result [%s]", result));
        dismissDialog();
        serviceInterface.onFinished(result);
    }

    protected void onCancelled(String result) {
        dismissDialog();
    }

}
