package com.xyyx.openvfd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xyyx.openvfd.Utils.Constants;
import com.xyyx.openvfd.Utils.Utils;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int networkType = getActiveNetworkInfo(context);
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            switch (networkType) {
                case -1: {
                    Utils.writeValue(Constants.LED_OFF, Constants.ETH);
                    Utils.writeValue(Constants.LED_OFF, Constants.WIFI);
                    break;
                }
                case 1: {
                    Utils.writeValue(Constants.LED_OFF, Constants.ETH);
                    Utils.writeValue(Constants.LED_ON, Constants.WIFI);
                    break;
                }
                case 9: {
                    Utils.writeValue(Constants.LED_ON, Constants.ETH);
                    Utils.writeValue(Constants.LED_OFF, Constants.WIFI);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    public int getActiveNetworkInfo(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }
}