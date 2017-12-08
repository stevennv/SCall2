package com.example.admin.scall.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.scall.activity.DetailContactActivity;
import com.example.admin.scall.activity.MainActivity;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;

import java.util.Date;

/**
 * Created by Admin on 11/24/2017.
 */

public class CallReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;
    private SqliteHelper db;
    private boolean isStyle;
    private DetailContactActivity mActivity;
    int state = 0;

    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                try {
//                    db.getStyleByPhone(number);
                    Intent intent = new Intent(context, DetailContactActivity.class);
                    InfoStyle infoStyle = db.getStyleByPhone(number);
                    intent.putExtra("Info", infoStyle);
                    context.startActivity(intent);
                    Toast.makeText(context, "Incoming Call Ringing", Toast.LENGTH_SHORT).show();
                    isStyle = true;
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("onCallStateChanged:", "onCallStateChanged: " + e.getMessage());
                    isStyle = false;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    Toast.makeText(context, "Outgoing Call Started", Toast.LENGTH_SHORT).show();
                }

//                try {
//                    InfoStyle infoStyle = db.getStyleById(number);
//                    Intent intent = new Intent(context, DetailContactActivity.class);
//                    intent.putExtra("Info", infoStyle);
//                    context.startActivity(intent);
//                } catch (Exception e) {
//                    Log.d("onCallStateChanged: ", "onCallStateChanged: " + e.getMessage());
//                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    Toast.makeText(context, "Ringing but no pickup" + savedNumber + " Call time " + callStartTime + " Date " + new Date(), Toast.LENGTH_SHORT).show();

                } else if (isIncoming) {
                    Toast.makeText(context, "Incoming " + savedNumber + " Call time " + callStartTime, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(context, "outgoing " + savedNumber + " Call time " + callStartTime + " Date " + new Date(), Toast.LENGTH_SHORT).show();

                }
                try {
                    DetailContactActivity mActivity = ((DetailContactActivity) context);
                    mActivity.finish();
                } catch (Exception e) {
                    Log.d("onCallStateChanged123: ", "onCallStateChanged: " + e.getMessage());
                }
                break;
        }
        lastState = state;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        db = new SqliteHelper(context);
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            final String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d("onReceive:", "onReceive: " + number);

            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChanged(context, state, number);
        }
    }
}

