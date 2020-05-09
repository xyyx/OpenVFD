package com.xyyx.openvfd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xyyx.openvfd.Utils.Constants;
import com.xyyx.openvfd.Utils.Utils;

import java.math.BigInteger;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String part1 ="034D04400";
        String part2 = "0000005C";
        int brightness = 1;

        int requestCode = intent.getExtras().getInt("requestCode");
        if (Constants.DEBUG)
            Log.d(Constants.TAG,"ALARM Received "+requestCode);
        if (requestCode == 1) {
            brightness = 1;
            String cmd = part1 + brightness + part2;
            byte[] cmd_byte = new BigInteger(cmd,16).toByteArray();
            Utils.writeCmd(Constants.LED_CMD,cmd_byte);
            Utils.writeValue(Constants.LED_ON,Constants.ALARM);
        } else {
            brightness = 5;
            String cmd = part1 + brightness + part2;
            byte[] cmd_byte = new BigInteger(cmd,16).toByteArray();
            Utils.writeCmd(Constants.LED_CMD,cmd_byte);
            Utils.writeValue(Constants.LED_OFF,Constants.ALARM);
        }
    }
}
