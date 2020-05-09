package com.xyyx.openvfd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xyyx.openvfd.Utils.Constants;
import com.xyyx.openvfd.Utils.Utils;

public class UsbChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                Utils.writeValue(Constants.LED_ON, Constants.USB);
            } else if (intent.getAction().equals("android.intent.action.MEDIA_EJECT") || intent.getAction().equals("android.intent.action.MEDIA_EJECT")) {
                Utils.writeValue(Constants.LED_OFF, Constants.USB);
            } else {
                return;
        }
    }
}