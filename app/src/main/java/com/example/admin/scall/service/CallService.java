package com.example.admin.scall.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.scall.receiver.CallReceiver;

/**
 * Created by admin on 12/10/2017.
 */

public class CallService extends Service {
    private IntentFilter mIntentFilter;
    private CallReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mIntentFilter = new IntentFilter();
        receiver = new CallReceiver();
        mIntentFilter.addAction("android.intent.action.PHONE_STATE");
        mIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(receiver, mIntentFilter);
        Log.d("onStartCommand:", "onStartCommand: 123123123");
        return START_STICKY;
    }

}
