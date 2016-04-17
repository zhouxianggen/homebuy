package com.hg.www.buyer.ui.event;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusApi {
    public static final String TAG = BusApi.class.getSimpleName();
    private static BusApi instance;
    private Bus bus;
    private Handler handler = new Handler(Looper.getMainLooper());

    private BusApi() {
        this.bus = new Bus(ThreadEnforcer.ANY);
    }

    public static synchronized BusApi getInstance() {
        if (instance == null) {
            instance = new BusApi();
        }
        return instance;
    }

    public <T> void postToSameThread(final T event) {
        bus.post(event);
    }

    public <T> void postToMainThread(final T event) {
        try {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        bus.post(event);
                    } catch(Exception e) {
                        Log.e(TAG, "POST TO MAIN THREAD: BUS LEVEL");
                        throw e;
                    }
                }
            });
        } catch(Exception e) {
            Log.e(TAG, "POST TO MAIN THREAD: HANDLER LEVEL");
            throw e;
        }
    }

    public <T> void register(T subscriber) {
        bus.register(subscriber);
    }

    public <T> void unregister(T subscriber) {
        bus.unregister(subscriber);
    }
}
